# Demo Angular with OSGI

Package angular into OSGI bundle

## Build

```shell
./gradlew generateBnd
./gradlew buildApp
```

## Install

```shell
feature:install war
bundle:install -s mvn:fr.fcolinet.fcconsulting:angular:1.0.0-SNAPSHOT
```

## Test

[Click here](http://localhost:8181/angular/)
