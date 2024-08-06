# Spring Boot Translation webapp

This is a Spring Boot web application. The project uses Maven for build and dependency management.

## Requirements

- JDK 22 or higher
- Docker (During the launch of the application, a working Docker engine is required)

## Getting Started

### Cloning the Repository

First, clone the repository to your local machine:

```bash
git clone https://github.com/DaryzDark/translateTwebApp.git
```

### Building the Application

You can start the application using Maven Wrapper. This ensures that the correct version of Maven is used.

#### Using Maven Wrapper

To run the application, execute:
``` bash
./mvnw spring-boot:run
```
On Windows, use:
```bash
mvnw.cmd spring-boot:run
```
### Running the Application

Once the application is built and run, you can run acsess it from your browser using http://localhost:8080. The default port is 8080.