# Backend Subdirectory

This directory contains the backend for the application, built with [Spring](http://spring.io/) and serves as the backend for the application, handling all the business logic and providing the API for the frontend.
The basic structure of the backend is based on Domain Driven Design (DDD).

## Overview

The structure of the backend is as follows:

main:
- `application`: Contains the buissness logic of the application.
- `domain`: Contains the domain models of the application.
- `infrastructure`: 
    - `persistance`: Contains the persistance logic of the application, i.e. repositories.
    - `rest`: Contains the REST API of the application.
- `resources`: Contains the configuration files for the application, e.g. the liquibase changelog and the application properties read by Spring.

test:
- `unit`: Contains the unit tests for the application.
- `integration`: Contains the integration tests for the application.

## Technologies

The backend is built using the folowing tech-stack:

Main:
- [Spring](http://spring.io/): A framework for building Java applications.

API:
- [JSON Web Token](https://jwt.io/): A standard for representing claims securely between two parties.

Database:
- [PostgreSQL](https://www.postgresql.org/): Object-relational database system.
- [Liquibase](https://www.liquibase.org/): Database management tool.

Other:
- [Lombok](https://projectlombok.org/): Boilerplate code generator.
- [Apache Commons](https://commons.apache.org/): Common problem solutions, mainly used for validation (i.e. `lang3`).

## Getting Started

In this project, we use [Maven](https://maven.apache.org/) as the build tool. Using `mvn`, first install all the dependencies:

```bash
mvn install # optional
```

Then, you can start the backend in development mode by running the following command:

```bash
mvn spring-boot:run
```

Note, that for all features to work, you also need to start the database.

To test the backend (i.e. integration test), you can run the following command:

```bash
mvn clean integration-test
```