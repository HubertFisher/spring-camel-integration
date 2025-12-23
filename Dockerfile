# ============ DEV ============
FROM gradle:8-jdk17-alpine

#INSTALL ADDITIONAL UTILITIES FOR DEVELOPMENT
RUN apk add --no-cache \
    curl \
    vim \
    bash \
    git \
    && rm -rf /var/cache/apk/*

WORKDIR /app

# COPY DEPENDENCIES & WRAPPER
COPY .env .
COPY gradle/wrapper gradle/wrapper
COPY build.gradle.kts .
COPY gradlew .
COPY gradlew.bat .
COPY settings.gradle.kts .

#INSTALL DEPENDENCIES
RUN ./gradlew dependencies --no-daemon

#PORT
EXPOSE 8080
#REMOTE DEBUGGING PORT
EXPOSE 8000

#DEV SETTINGS FOR GRADLE
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"
ENV GRADLE_USER_HOME=/home/gradle/.gradle

#START WITH live Reload
CMD ["./gradlew", "bootRun", "--stacktrace", "--info", "-Dspring.devtools.restart.enabled=true", "-Dspring.devtools.livereload.enabled=true"]