# Release Manager

Submitted to InterSystems Java Contest 2023

InterSystems [IRIS](https://www.intersystems.com/data-platform/) is a high-performance database that powers transaction processing applications around the world.

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

### Prepare dependencies

InterSystems Java technologies are not available on Maven Central, so you need to grab JDBC connectors and other jar files from https://intersystems-community.github.io/iris-driver-distribution/.

This project contains the jar files at `src/main/resources/lib`.

In order to build this project, go to the `Backend` root folder and type:

```shell script
mvn install:install-file -Dfile=${PWD}/src/main/resources/lib/intersystems-jdbc-3.7.1.jar -DgroupId=com.intersystems -DartifactId=intersystems-jdbc -Dversion=3.7.1 -Dpackaging=jar
```

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