plugins {
    id("java")
}

group = "com.programacion"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

// https://mvnrepository.com/artifact/net.java.dev.jna/jna
    implementation("net.java.dev.jna:jna:5.16.0")
}

tasks.test {
    useJUnitPlatform()
}