# TimeUtility
A collection of facilities for making life easier when dealing with Java 8 date and time.

This project builds a JAR which can then be used within your projects.

## Building

The following notes all assume that you have access to the command line and know what to do there, have a fairly recent version of Maven, and at least Java 8.

To build the  JAR after checking out the project:

```
git clone https://github.com/TheBellman/timeutility.git
cd timeutility
mvn clean package
```

After a bit of grinding, the JAR should be available at

```
target/TimeUtility-1.0-SNAPSHOT.jar 
```

A site report can be built locally as well, which will provide you with JavaDoc and test coverage details:

```
mvn site
```

This will result in a local web site that can be accessed via

```
target/site/index.html
```
**Note:** A current release version can be found in my private Artifactory at the URL below. There are instructions there for how to include this repository in your build cycle.

```
http://54.209.160.169:8081/artifactory/webapp/#/home
```

## Use
_to be completed_