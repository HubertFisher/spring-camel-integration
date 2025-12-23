plugins {
    id("java")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
}

dependencies {
    // =================== SPRING BOOT ===================
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    // =================== APACHE CAMEL ===================
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-jackson-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-jpa-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-ftp-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-bean-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-file-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-timer-starter:4.0.0")
    implementation("org.apache.camel.springboot:camel-micrometer-starter:4.0.0")

    // =================== DATABASE ===================
    runtimeOnly("org.postgresql:postgresql")

    // =================== UTILITIES ===================
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // =================== TESTING ===================
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("app.jar")
    layered {
        enabled = true
    }
}


tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    jvmArgs = listOf(
        "-Xmx512m",
        "-Xms256m",
        "-Dspring.profiles.active=dev"
    )
}
