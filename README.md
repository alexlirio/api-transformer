# API Transformer

The Transformers are at war and you are in charge of settling the score! This API evaluates who wins a fight between the Autobots and the Decepticons.

## How to Run 

This application is packaged as a jar which has Tomcat 9 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 11 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:

```
        mvn spring-boot:run -Dspring-boot.run.profiles=test
or
        java -jar -Dspring.profiles.active=test target/api-transformer-0.0.1-SNAPSHOT.jar
```

* Check the stdout or log/api-transformer.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
...
2021-03-31 16:00:35.961  INFO 429671 --- [restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-03-31 16:00:36.159  INFO 429671 --- [restartedMain] com.company.ApiTransformerApplication    : Started ApiTransformerApplication in 3.491 seconds (JVM running for 3.93)
```

## Tests

The application tests are organized by repositories, services, and controllers. You can run all tests by running the ```mvn test``` command, or you can run all tests for a specific package using one of the commands below:

```
        mvn '-Dtest=com.company.domain.repository.*Test' test
or
        mvn '-Dtest=com.company.api.service.*Test' test
or
        mvn '-Dtest=com.company.api.controller.*Test' test
```

* Check the results on stdout

Once the application tests runs you should see something like this

```
...
[INFO] Results:
[INFO] 
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  12.413 s
[INFO] Finished at: 2021-04-01T08:24:27-03:00
[INFO] ------------------------------------------------------------------------

```

## About the Service

The service is just a War Transformer API REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in ```com.company.api.controller.TransformerController``` on **port 8080**. (see below)

More interestingly, you can start calling the operational endpoint ```/acuator/health``` and ```/acuator/info``` (these are available on **port 8080**)

Here is what this little application demonstrates: 

* Full integration with the latest **Spring** Framework.
* Packaging as a single jar with embedded container (tomcat 9).
* Demonstrates how to set up healthcheck, swagger, environment, etc.
* Writing a REST service using JSON request / response.
* Exception mapping from application exceptions to the right HTTP response with exception details in the body.
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern.
* Demonstrates MockMVC test framework with associated libraries.
* All APIs are "self-documented" by Swagger2.

Here are some endpoints you can call:

### Get information about system health.

```
http://localhost:8080/actuator/health
http://127.0.0.1:8080/actuator/info
```

### Create a Transformer

```
POST /api/v1/registry/transformers
Accept: application/json
Content-Type: application/json
{
    "name": "Megatron",
    "team": "DECEPTICON",
    "strength": 9,
    "intelligence": 8,
    "speed": 7,
    "endurance": 6,
    "rank": 5,
    "courage": 4,
    "firepower": 3,
    "skill": 2
}

RESPONSE: HTTP 201 (Created)
{
    "id": 9,
    "name": "Megatron",
    "team": "DECEPTICON",
    "strength": 9,
    "intelligence": 8,
    "speed": 7,
    "endurance": 6,
    "rank": 5,
    "courage": 4,
    "firepower": 3,
    "skill": 2
}
```

### Update a Transformer

```
PUT /api/v1/registry/transformers/1
Accept: application/json
Content-Type: application/json
{
    "name": "Optimus Prime",
    "team": "AUTOBOT",
    "strength": 9,
    "intelligence": 9,
    "speed": 8,
    "endurance": 7,
    "rank": 6,
    "courage": 5,
    "firepower": 4,
    "skill": 3
}

RESPONSE: HTTP 200 (Ok)
{
    "id": 1,
    "name": "Optimus Prime",
    "team": "AUTOBOT",
    "strength": 9,
    "intelligence": 9,
    "speed": 8,
    "endurance": 7,
    "rank": 6,
    "courage": 5,
    "firepower": 4,
    "skill": 3
}
```

### Delete a Transformer

```
DELETE /api/v1/registry/transformers/1

RESPONSE: HTTP 204 (No Content)
```

### List of Transformers paginated

```
GET /api/v1/registry/transformers?page=0&size=20

RESPONSE: HTTP 200 (Ok)
{
    "content": [
        {
            "id": 1,
            "name": "Optimus Prime",
            "team": "AUTOBOT",
            "strength": 10,
            "intelligence": 9,
            "speed": 8,
            "endurance": 7,
            "rank": 6,
            "courage": 5,
            "firepower": 4,
            "skill": 3
        },
        {
            "id": 2,
            "name": "Bumblebee",
            "team": "AUTOBOT",
            "strength": 9,
            "intelligence": 8,
            "speed": 7,
            "endurance": 6,
            "rank": 5,
            "courage": 4,
            "firepower": 3,
            "skill": 2
        },
        ...
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 20,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 8,
    "last": true,
    "first": true,
    "size": 20,
    "number": 0,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 8,
    "empty": false
}
```

### War

```
POST /api/v1/war/transformers
Accept: application/json
Content-Type: application/json

[
    1,2,3,4,
    5,6,7,8
]

Response: HTTP 200 (Ok)
{
    "battles": 3,
    "championTeam": "DECEPTICON",
    "losingSurvivors": [
        {
            "id": 2,
            "name": "Bumblebee",
            "team": "AUTOBOT",
            "strength": 9,
            "intelligence": 8,
            "speed": 7,
            "endurance": 6,
            "rank": 5,
            "courage": 4,
            "firepower": 3,
            "skill": 2
        },
        {
            "id": 1,
            "name": "Optimus Prime",
            "team": "AUTOBOT",
            "strength": 10,
            "intelligence": 9,
            "speed": 8,
            "endurance": 7,
            "rank": 6,
            "courage": 5,
            "firepower": 4,
            "skill": 3
        }
    ]
}
```

### To view Swagger 2 API docs

Run the server and browse to http://localhost:8080/swagger-ui/#/

### To view your H2 in-memory database

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8080/h2-console. Default username is 'sa' with a blank password. Make sure you disable this in your production profiles. For more, see https://goo.gl/U8m62X

# Running the project with a Relational Database (Developer Profile )

This project uses an in-memory database so that you don't have to install a database in order to run it. However, converting it to run with another relational database such as PostgreSQL is very easy.

Here is what you would do to back the services with PostgreSQL, for example: 

### Create a database
  
Create a database named 'api-transformer', and create the tables with the file 'src/main/resources/create-dev.sql'.

### Configure your credentials in the 'src/main/resources/application-dev.properties' file: 

```
...
spring.datasource.url=jdbc:postgresql://localhost:5432/api-transformer
spring.datasource.username=postgres
spring.datasource.password=mysecretpassword
...
```

### Then run is using the 'dev' profile:

```
        mvn spring-boot:run -Dspring-boot.run.profiles=dev
or
        java -jar -Dspring.profiles.active=dev target/api-transformer-0.0.1-SNAPSHOT.jar
```

# Attaching to the app remotely from your IDE

Run the service with these command line options:

```
        mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
or
        java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=test -Ddebug -jar target/api-transformer-0.0.1-SNAPSHOT.jar
```
and then you can connect to it remotely using your IDE. For example, from IntelliJ You have to add remote debug configuration: Edit configuration -> Remote.

# Questions and Comments: alexlirioti@gmail.com
