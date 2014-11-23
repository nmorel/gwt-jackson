gwt-jackson
=====
gwt-jackson is a [GWT](http://www.gwtproject.org/) JSON serializer/deserializer mechanism based on [Jackson 2.x annotations](https://github.com/FasterXML/jackson-annotations). Jackson 1.x annotations (`org.codehaus.jackson.*`) are not supported.

Lots of annotation are already supported, you can find many use cases in the [tests](gwt-jackson/src/test/java/com/github/nmorel/gwtjackson).

Check the [wiki](https://github.com/nmorel/gwt-jackson/wiki) for more informations.

Quick start
-------------
Add `<inherits name="com.github.nmorel.gwtjackson.GwtJackson" />` to your module descriptor XML file.

Then just create an interface extending `ObjectReader`, `ObjectWriter` or `ObjectMapper` if you want to read JSON, write an object to JSON or both.

Here's an example :

```java
public class TestEntryPoint implements EntryPoint {

    public static interface PersonMapper extends ObjectMapper<Person> {}

    public static class Person {

        private final String firstName;
        private final String lastName;

        @JsonCreator
        public Person( @JsonProperty( "firstName" ) String firstName,
                       @JsonProperty( "lastName" ) String lastName ) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    @Override
    public void onModuleLoad() {
        PersonMapper mapper = GWT.create( PersonMapper.class );

        String json = mapper.write( new Person( "John", "Doe" ) );
        GWT.log( json ); // > {"firstName":"John","lastName":"Doe"}

        Person person = mapper.read( json );
        GWT.log( person.getFirstName() + " " + person.getLastName() ); // > John Doe
    }
}
```

Maven
-------------

```xml
<dependency>
  <groupId>com.github.nmorel.gwtjackson</groupId>
  <artifactId>gwt-jackson</artifactId>
  <version>0.7.0</version>
  <scope>provided</scope>
</dependency>
```

You can also get maven snapshots using the following repository :

```xml
<repository>
  <id>oss-sonatype-snapshots</id>
  <url>https://oss.sonatype.org/content/repositories/snapshots</url>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
```

Copyright and license
-------------

Copyright 2014 Nicolas Morel under the [Apache 2.0 license](LICENSE).
