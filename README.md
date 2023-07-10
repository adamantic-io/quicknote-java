# Quicknote Library for Java
## Introduction
This library is an attempt to create an easy-to-integrate library
to send and receive notifications across distributed systems, including
web applications and - in the future - mobile devices.

## Using the library as a developer
### Maven repository
This is a work in progress, come back soon for instructions on how to modify
your `pom.xml` file to use the library.

### Code examples
The library is designed to be as easy to use as possible.
Documentation is being written.
Please refer to the 
[SenderReceiverTest](./quicknote-java-amqp/src/test/java/io/adamantic/quicknote/amqp/SenderReceiverTest.java)
for a few working examples.

## Build instructions
It is sufficient to run `./mvnw clean install` to build the library.

## Running the tests

**Pre-requisites**: You should have `docker-compose` and a running docker daemon.

The quicknote library is built to fetch configuration properties
from the system environment and from a yaml configuration file.
The tests need the default `env.example` configuration file to be copied
into the `.env` file in the main directory of each submodule.
For a quick start:
* Start the docker containers in the `docker/quicknote` directory
* Copy the `env.example` file to `.env` in the main directory of each submodule
* Run `./mvnw test` in the main directory of the project

you can run the following commands:
```
docker-compose -f ./docker/quicknote/docker-compose.yml up -d
./cp-env.sh
./mvnw test
```
