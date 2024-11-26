plugins {
    id("java")
}

group = "com.programacion"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Core LWJGL
    implementation("org.lwjgl:lwjgl:3.3.4")
    runtimeOnly("org.lwjgl:lwjgl:3.3.4:natives-windows")

    // GLFW
    implementation("org.lwjgl:lwjgl-glfw:3.3.4")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.4:natives-windows")

    // OpenGL
    implementation("org.lwjgl:lwjgl-opengl:3.3.4")
    runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.4:natives-windows")
}
