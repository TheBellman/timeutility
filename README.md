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

### InstantRange

This class represents a series of 'ticks' at regular intervals between a from and to Instant (inclusive). It implements `Collection<Instant>` so you automatically get all the semantics of being able to use it as a Collection. Construction is simple:

```
Instant from = ...
Instant to = ...

InstantRange range = new InstantRange(from, to, ChronoUnit.HOURS);
```

With the obtained instant, you are then free to treat it as a simple `Collection<Instant>`. Note that the obtained instance is immutable, which means that any attempt to call the `Collection` methods to alter the content of the virtual `Collection` will be rejected with an `UnsupportedOperationException`.

Any of the parameters in the constructor can be null, the object will sort out defaults for you so that:

-  'to' becomes 'now', truncated to the supplied unit
-  'from' becomes 'now - unit', truncated to the supplied unit
-  'unit' defaults to HOURS

One thing that is not _entirely_ obvious is that not all `ChronoUnit` can be used - for whatever reason the Java 8 date/time package puts some rather unintuitive restrictions on what can be applied to an `Instant`, and in practice no unit larger than DAYS can be used. At a later version I may put some constraints around this parameter to avoid unexpected exceptions during construction.