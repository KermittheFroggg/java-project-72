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
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

    implementation("io.javalin:javalin:5.6.1")
    implementation ("io.javalin:javalin-rendering:5.4.2")
    implementation("org.slf4j:slf4j-simple:2.0.7")

    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("gg.jte:jte:3.0.1")

    implementation ("com.konghq:unirest-java:3.11.09")
    implementation("com.konghq:unirest-java-parent:4.0.0-RC7")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation ("org.jsoup:jsoup:1.16.1")

}

tasks.test {
    useJUnitPlatform()
}