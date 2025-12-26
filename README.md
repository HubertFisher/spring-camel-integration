# User Export Pipeline

Automated ETL pipeline for exporting user data from PostgreSQL to JSON files on FTP server using Spring Boot and Apache Camel.

## Features
- Scheduled database polling for new users
- JSON transformation (one file per user)
- Secure FTP upload with retry mechanism
- Status tracking to prevent duplicate exports
- Docker containerization

## Tech Stack
- Java 17
- Spring Boot 3
- Apache Camel 3
- PostgreSQL
- Docker & Docker Compose

## Quick Start

### Prerequisites
- Java 17+
- Gradle 8.5+
- Docker & Docker Compose

### Run with Docker
```bash
git clone https://github.com/yourname/user-export-pipeline.git
cd user-export-pipeline
cp .env.example .env
# Edit .env with your FTP credentials
docker-compose up -d