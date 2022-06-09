# Spring "Functional" Application

## Country API

API for create and find countries.

## Goal

Create a POC application for an API using Spring Boot 3.0.0 with a "functional" approach and the following dependencies,
Spring Reactive Web, Spring
Data Reactive MongoDB and Spring Validation.

With these dependencies it was possible to create an API that create and find a country.

## Composition

The project is composed of the domain module and the application module.

## Build

To build the project, Java 17 is required.

Then run the command below.

```shell
gradle clean build
```

## Run

After the build, the artifact will be available at: **/application/build/libs/**

Just run the application artifact

```shell
java -jar ./application/build/libs/application-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Find country

```
    GET /v1/country/A1 HTTP/1.1
    Host: localhost:8080
```

### Find all countries

```
    GET /v1/country HTTP/1.1
    Host: localhost:8080
```

### Create country

```
    POST /v1/country HTTP/1.1
    Content-Type: application/json
    Host: localhost:8080
    Content-Length: 44
    
    {
        "name": "Brazil",
        "abbreviation": "BR"
    }
```