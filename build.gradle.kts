group = "com.aipia"
version = "0.0.1"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.springboot)
    alias(libs.plugins.spring.dependency.management)
}

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly(libs.development.springboot.devtools)

    implementation(libs.bundles.kotlin)
    implementation(libs.dependency.springboot.web)
    implementation(libs.dependency.springboot.validation)
    implementation(libs.dependency.springboot.security)
    implementation(libs.bundles.jjwt)
    implementation(libs.dependency.springboot.cache)
    implementation(libs.dependency.springboot.jpa)

    runtimeOnly(libs.runtimeonly.h2)

    testImplementation(libs.test.junit5)
    testImplementation(libs.test.mockito)
    testImplementation(libs.test.springboot)
    testImplementation(libs.test.springboot.security)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}