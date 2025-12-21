plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // =================== SPRING BOOT ===================
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // =================== APACHE CAMEL ===================
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-jackson-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-jpa-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-ftp-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-sql-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-bean-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-file-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-timer-starter:3.21.0")
    implementation("org.apache.camel.springboot:camel-micrometer-starter:3.21.0")

    // =================== DATABASE ===================
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.hibernate:hibernate-core:6.2.6")

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