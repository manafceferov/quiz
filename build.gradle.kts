plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("gg.jte.gradle") version "3.1.16"
    kotlin("jvm") version "1.9.10"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "com.jafarov"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("gg.jte:jte-spring-boot-starter-3:3.1.16")
    implementation("org.liquibase:liquibase-core:4.20.0")
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    implementation("org.springframework.session:spring-session-core")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5:3.0.4.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("org.projectlombok:lombok:1.18.38")
    implementation("org.modelmapper:modelmapper:3.1.1")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.ocpsoft.prettytime:prettytime:5.0.3.Final")

    // --- Liquibase Runtime dependencies ---
    liquibaseRuntime("org.liquibase:liquibase-core:4.20.0")
    liquibaseRuntime("org.postgresql:postgresql")
    liquibaseRuntime("info.picocli:picocli:4.7.4")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

jte {
    generate()
    binaryStaticContent = true
}

tasks.named("compileKotlin") {
    dependsOn("generateJte")
}

tasks.withType<Test> {
    useJUnitPlatform()
    dependsOn("generateJte")
}

// Liquibase konfiqurasiyası
liquibase {
    activities.register("main") {
        arguments = mapOf(
            "changelogFile" to "src/main/resources/db/changelog/db.changelog-master.yaml",
            "url" to "jdbc:postgresql://192.168.1.101:5433/quizdb",
            "username" to "quizuser",
            "password" to "quizpassword",
            "driver" to "org.postgresql.Driver"
        )
    }
    runList = "main"
}
