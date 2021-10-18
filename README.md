# KumuluzEEHelloWorld

Simple 2-day-of-coding project for getting started with KumuluzEE. To run:

```
mvn clean package
java -jar api/target/api-1.0-SNAPSHOT.jar
```

... or with Docker:
```
mvn clean package
docker build -t microservice_image .
docker run --name microservice_container microservice_image
```

The service requires a database, Postgres, to be accessible. Set the address, where this service can be accessed, in the `config.yaml` file in the `api` module.


The api specification is available on `<BASE_URL>/openapi`.


Functionalities:
* a set of items, that can be accessed and modified through the `/items` endpoint, is available;
* a set of users (`/users`) can manage their grocely lists (`/users/{user_id}/lists/{list_id}`). Users can create/delete lists and can add and remove items from a specific grocery list.
