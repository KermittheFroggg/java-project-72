import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.3"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-lang:commons-lang:2.6")

    implementation("org.slf4j:slf4j-simple:2.0.7")

    implementation("io.javalin:javalin:5.6.2")
    implementation("io.javalin:javalin-bundle:5.6.2")
    implementation("io.javalin:javalin-rendering:5.6.2")

    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("gg.jte:jte:3.0.1")

    implementation ("com.konghq:unirest-java:3.11.09")
    implementation("com.konghq:unirest-java-parent:4.0.0-RC7")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation ("org.jsoup:jsoup:1.16.1")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.mockito:mockito-core:3.+")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))


}

tasks.test {
    useJUnitPlatform()
    // https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
}