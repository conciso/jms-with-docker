# jms-with-docker
Repository for conciso blog post at http://conciso.de/blog/2017/01/07/zugriff-auf-jms-queues-in-einem-docker-container/

The maven project contains two submodules. The restjmsexample contains the source code for the rest interface and will be 
deployed with the help of the docker-maven-plugin into the wildfly in the docker container. The jms-client contains the
jms consumer example and creates a one jar which can be run without configuring any additional classpath elements.

## Run the docker container ##
You can create and run the docker container by using the following commands.

Build the docker container:

```sh
cd docker
docker build -t blog-wildfly .
```

Run the docker container:
```sh
docker run --rm -p9990:9990 -p8080:8080 blog-wildfly
```

## Building the source code and deploy ##
The project can be build and the rest implementation can be deployed using the following command:
```sh
mvn clean install
```

## Run the jms-client ##
The project is build with the previous maven command and can be run with the following command:
```sh
cd jms-client/target
java -jar jms-client.jar
```
