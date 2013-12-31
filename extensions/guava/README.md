gwt-jackson-guava
=====
This extension contains serializer and deserializer for Guava types like Optional or ImmutableList. For a complete support list,
check the wiki or [the configuration class](src/main/java/com/github/nmorel/gwtjackson/guava/rebind/GuavaConfiguration.java).

To use it, add the library to your classpath (for maven, the artifactId is `gwt-jackson-guava`) and add `<inherits name="com.github.nmorel.gwtjackson.guava.GwtJacksonGuava" />` to your module descriptor XML file.


Copyright and license
-------------

Copyright 2013 Nicolas Morel under the [Apache 2.0 license](LICENSE).
