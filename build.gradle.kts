plugins {
    id("java")
    application
    id("io.freefair.lombok") version "9.0.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.arkn37"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Apache HttpClient for HTTP requests
    implementation("org.apache.httpcomponents.client5:httpclient5:5.5.1")

    // Gson for JSON processing
    implementation("com.google.code.gson:gson:2.11.0")

    // PostgreSQL JDBC driver
    implementation("org.postgresql:postgresql:42.6.0")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "17.0.14"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("org.arkn37.Main")
}
