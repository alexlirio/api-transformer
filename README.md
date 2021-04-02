# API Transformer

The Transformers are at war and you are in charge of settling the score! This API evaluates who wins a fight between the Autobots and the Decepticons.

**The basic rules of the war**
* The transformers are split into two teams based on if they are Autobots or Decepticons.
* The teams should be sorted by rank and faced off one on one against each other in order to determine a victor, the loser is eliminated.
* A battle between opponents uses the following rules:  
  ** If any fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent, the opponent automatically wins the face-off regardless of overall rating (opponent has ran away).  
  ** Otherwise, if one of the fighters is 3 or more points of skill above their opponent, they win the fight regardless of overall rating  
* The winner is the Transformer with the highest overall rating.
* In the event of a tie, both Transformers are considered destroyed.
* Any Transformers who don’t have a fight are skipped (i.e. if it’s a team of 2 vs. a team of 1, there’s only going to be one battle).
* The team who eliminated the largest number of the opposing team is the winner.  
  
**Special rules**  
* Any Transformer named Optimus Prime or Predaking wins his fight automatically regardless of any other criteria.
* In the event either of the above face each other (or a duplicate of each other), the game immediately ends with all competitors destroyed.  
  
**For example, given the following**  
* To the input:
  ** Soundwave, DECEPTICON, 8, 9, 2, 6, 7, 5, 6, 10
  ** Bluestreak, AUTOBOT, 6, 6, 7, 9, 5, 2, 9, 7
  ** Hubcap: AUTOBOT, 4, 4, 4, 4, 4, 4, 4, 4  
* The output should be:
  ** 1 battle Winning team (Decepticons): Soundwave
  ** Survivors from the losing team (Autobots): Hubcap

**List of default transformers created on startup**  
* #1 Optimus Prime, AUTOBOT, 10, 9, 8, 7, 6, 5, 4, 3
* #2 Bumblebee, AUTOBOT, 9, 8, 7, 6, 5, 4, 3, 2
* #3 Bluestreak, AUTOBOT, 6, 6, 7, 9, 5, 2, 9, 7
* #4 Hubcap, AUTOBOT, 4, 4, 4, 4, 4, 4, 4, 4
* #5 Predaking, DECEPTICON, 10, 9, 8, 7, 6, 5, 4, 3
* #6 Soundwave, DECEPTICON, 8, 9, 2, 6, 7, 5, 6, 10
* #7 Shockwave, DECEPTICON, 8, 7, 6, 5, 4, 3, 2, 1
* #8 Arcee, AUTOBOT, 6, 5, 4, 3, 2, 1, 1, 1

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

### Get information about system health

```
http://localhost:8080/actuator/health
http://localhost:8080/actuator/info
```

### To view Swagger 2 API docs

```
http://localhost:8080/swagger-ui/#/transformer-controller
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
        java -jar -Dspring.profiles.active=dev target/api-transformer-1.0.0.jar
```

# GitHub CI (Heroku)

The project has a GitHub workflow created and following the Gitflow standard is mandatory. Every push to 'main' branch will deploy a new version of this app on Heroku [api-transformer.herokuapp.com](http://api-transformer.herokuapp.com/actuator/info). Deploys happen automatically if the commit passes all automated tests.

Attention: The first request to Heroku may take a while but from the second request it is faster. The Heroku free account sleeps the application after some time without use.

Here are some endpoints you can call:

### Get information about system health (Heroku)

```
https://api-transformer.herokuapp.com/actuator/health
https://api-transformer.herokuapp.com/actuator/info
```

### To view Swagger 2 API docs (Heroku)

```
https://api-transformer.herokuapp.com/swagger-ui/#/transformer-controller
```

### All Transformer Controller methods (Heroku)
 More details, as a body, of the methods below can be found in the 'About the Service' section on this document.

```
POST https://api-transformer.herokuapp.com/api/v1/registry/transformers
PUT https://api-transformer.herokuapp.com/api/v1/registry/transformers/1
DELETE https://api-transformer.herokuapp.com/api/v1/registry/transformers/1
GET https://api-transformer.herokuapp.com/api/v1/registry/transformers?page=0&size=20
POST https://api-transformer.herokuapp.com/api/v1/war/transformers
```

# Attaching to the app remotely from your IDE

Run the service with these command line options:

```
        mvn spring-boot:run -Dagentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
or
        java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -Dspring.profiles.active=test -Ddebug -jar target/api-transformer-1.0.0.jar
```
and then you can connect to it remotely using your IDE. For example, from Eclipse you have to add a new debug configuration: Run -> Debug Configurations -> Remote Java Application -> New Configuration.
