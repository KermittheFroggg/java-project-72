package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.TestUtils;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

import io.javalin.Javalin;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;

import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

class AppTest {

    private static MockWebServer mockServer;
    private Javalin app;
    private Map<String, Object> existingUrl;
    private Map<String, Object> existingUrlCheck;
    private HikariDataSource dataSource;

    private static final Logger LOGGER = Logger.getLogger(AppTest.class.getName());

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws IOException {
        Path filePath = getFixturePath(fileName);
        return Files.readString(filePath).trim();
    }

    private static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        MockResponse mockedResponse = new MockResponse()
                .setBody(readFixture("index.html"));
        mockServer.enqueue(mockedResponse);
        mockServer.start();
        FileHandler fileHandler = new FileHandler("logs.txt");
        fileHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fileHandler);
        LOGGER.setLevel(Level.FINE);
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        app = App.getApp();

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());

        dataSource = new HikariDataSource(hikariConfig);

        var schemaStream = AppTest.class.getClassLoader().getResourceAsStream("schema.sql");
        if (schemaStream == null) {
            throw new FileNotFoundException("Could not find schema.sql");
        }
        var sql = new BufferedReader(new InputStreamReader(schemaStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        String url = "https://en.hexlet.io";

        TestUtils.addUrl(dataSource, url);
        existingUrl = TestUtils.getUrlByName(dataSource, url);

        TestUtils.addUrlCheck(dataSource, (long) existingUrl.get("id"));
        existingUrlCheck = TestUtils.getUrlCheck(dataSource, (long) existingUrl.get("id"));
    }

    @AfterEach
    public void tearDown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @Nested
    class RootTest {
        @Test
        void testIndex() {
            JavalinTest.test(app, (server, client) -> {
                assertThat(client.get("/").code()).isEqualTo(200);
            });
        }
    }

    @Nested
    class UrlTest {

        @Test
        void testIndex() {
            JavalinTest.test(app, (server, client) -> {
                var response = client.get("/urls");
                var responseBody = response.body().string();
                LOGGER.info(responseBody);
                assertThat(response.code()).isEqualTo(200);
                assertThat(responseBody)
                        .contains(existingUrl.get("name").toString())
                        .contains(existingUrlCheck.get("status_code").toString());
            });
        }

        @Test
        void testShow() {
            JavalinTest.test(app, (server, client) -> {
                var response = client.get("/urls/" + existingUrl.get("id"));
                var responseBody = response.body().string();
                assertThat(response.code()).isEqualTo(200);
                assertThat(responseBody)
                        .contains(existingUrl.get("name").toString())
                        .contains(existingUrlCheck.get("status_code").toString());
            });
        }

        @Test
        void testStore() {

            String inputUrl = "https://ru.hexlet.io";

            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=" + inputUrl;
                assertThat(client.post("/urls", requestBody).code()).isEqualTo(200);

                var response = client.get("/urls");
                var responseBody = response.body().string();
                assertThat(response.code()).isEqualTo(200);
                assertThat(responseBody)
                        .contains(inputUrl);
                try {
                    Thread.sleep(1000); // 1000 milliseconds = 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                var actualUrl = TestUtils.getUrlByName(dataSource, inputUrl);
                assertThat(actualUrl).isNotNull();
                assertThat(actualUrl.get("name").toString()).isEqualTo(inputUrl);
            });
        }
    }

    @Nested
    class UrlCheckTest {

        @Test
        void testStore() {
            String url = mockServer.url("/").toString().replaceAll("/$", "");

            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=" + url;
                assertThat(client.post("/urls", requestBody).code()).isEqualTo(200);

                var actualUrl = TestUtils.getUrlByName(dataSource, url);
                assertThat(actualUrl).isNotNull();
                System.out.println("\n!!!!!");
                System.out.println(actualUrl);

                System.out.println("\n");
                assertThat(actualUrl.get("name").toString()).isEqualTo(url);

                client.post("/urls/" + actualUrl.get("id") + "/checks");

                assertThat(client.get("/urls/" + actualUrl.get("id")).code())
                        .isEqualTo(200);

                var actualCheck = TestUtils.getUrlCheck(dataSource, (long) actualUrl.get("id"));
                assertThat(actualCheck).isNotNull();
                assertThat(actualCheck.get("title")).isEqualTo("Test page");
                assertThat(actualCheck.get("h1")).isEqualTo("Do not expect a miracle, miracles yourself!");
                assertThat(actualCheck.get("description")).isEqualTo("statements of great people");
            });
        }

        @Test
        public void testUrlCheckPage() throws SQLException {
            Date date = new Date();
            Timestamp createdAt = new Timestamp(date.getTime());
            var url = new Url("https://www.example.com", createdAt);
            UrlRepository.save(url);
            JavalinTest.test(app, (server, client) -> {
                var response = client.post("/urls/" + url.getId() + "/checks");
                assertThat(response.code()).isEqualTo(200);
            });
        }

        @Nested
        class UrlDeleteTest {
            @Test
            void testDelete() {
                JavalinTest.test(app, (server, client) -> {
                    var response = client.delete("/urls/1");
                    assertThat(response.code()).isEqualTo(404);
                    var getResponse = client.get("/urls/1");
                    assertThat(getResponse.code()).isEqualTo(200);
                });
            }
        }

        @Nested
        class UrlDeleteTest2 {
            @Test
            void testDelete() {
                JavalinTest.test(app, (server, client) -> {
                    var response = client.delete("/urls/" + existingUrl.get("id"));
                    assertThat(response.code()).isEqualTo(404);
                    var getResponse = client.get("/urls/" + existingUrl.get("id"));
                    assertThat(getResponse.code()).isEqualTo(200);
                });
            }
        }
    }
}
