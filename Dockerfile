# ============ DEV ============
FROM gradle:8-jdk17-alpine

WORKDIR /app

# COPY DEPENDENCIES
COPY build.gradle.kts settings.gradle.kts gradlew.bat gradlew .env ./

#INSTALL DEPENDENCIES
RUN gradle dependencies --no-daemon

#PORT
EXPOSE 8080

#START
CMD ["gradle", "bootRun", "--no-daemon", "-Dorg.gradle.vfs.watch=false"]