# Release Manager

Submitted to InterSystems Java Contest 2023

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the InterSystems IRIS Data Platform

```shell script
docker run --rm --name iris -p 1972:1972 -p 52773:52773 intersystemsdc/iris-community:2023.2-zpm
```
Open http://localhost:52773/csp/sys/UtilHome.csp to change the password.

```
Username: SuperUser
Change password from SYS to admin
```

If you need to use another password, you can provide it on the property `quarkus.datasource.password` in the application.properties file located at `src/main/resources`.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

Or you can use [SDK Man](https://sdkman.io/) and just run
```shell script
quarkus dev
```


## Packaging and running the application in container

The application can be packaged using:
```shell script
./mvnw package -Dquarkus.native.container-build=true
```