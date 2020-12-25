# Airport REST Service

A Spring Boot application providing Airport REST services 

## Requirements
- JDK 11
- Maven

## Running the application
Before running the application, fill the required properties in `application.properties` file.
Other properties are for customization purposes and don't need to be changed.
### Properties
**Database connection:**
```
spring.datasource.url=jdbc:mysql://HOST:PORT/DBNAME?useUnicode=yes&characterEncoding=UTF-8&useLegacyDatetimeCode=false
spring.datasource.username=username
spring.datasource.password=password
```

### Run
There are several ways to run a Spring Boot application on your local machine.
One way is to execute the `main` method in the `com.milicaradovanovic.daon.AirportRestServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Database
Alternatively you can run `airport.sql` from the chosen DB editor. Running this script will provide you already populated `airport` database.