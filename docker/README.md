# Docker Subdirectory

This directory contains the dockerfiles for the application and creates a dockerized postgres database for the application.

## Getting Started

To start the applications database, you need to have docker and docker-compose installed. Then, you can start the database by running the following command:

```bash
docker-compose -f docker-compose.dev.yml up --remove-orphans
```

If you want to clean up the database, you can run the following command:

```bash
docker-compose -f docker-compose.dev.yml down --remove-orphans
```

This is mostly useful if you are currently adding new tables to the database. Note that this will also remove the database volume, so you will lose all data. Also, note that with this you might circumvent the liquibase migrations, invalidating the database changelog and causing problems when you try to migrate changes to a production database.