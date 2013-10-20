gwt-jackson [![Build Status](https://nmorel.ci.cloudbees.com/buildStatus/icon?job=gwt-jackson)](https://nmorel.ci.cloudbees.com/job/gwt-jackson/)
=====
gwt-jackson is a [GWT](http://www.gwtproject.org/) JSON serializer/deserializer mechanism based on [Jackson annotations](https://github.com/FasterXML/jackson-annotations).

It is currently under development but lots of stuff is already working. You can find many use cases in the [tests](https://github.com/nmorel/gwt-jackson/tree/master/src/test/java/com/github/nmorel/gwtjackson).

Quick start
-------------
Add `<inherits name="com.github.nmorel.gwtjackson.GwtJackson" />` to your module descriptor XML file.

Then just create an interface extending `ObjectReader`, `ObjectWriter` or `ObjectMapper` if you want to read JSON, write an object to JSON or both.

Here's an example :

```java
public static interface PersonMapper extends ObjectMapper<Person> {}

public static class Person {

    private String firstName;
    private String lastName;

    @JsonCreator
    public Person( @JsonProperty( "firstName" ) String firstName, @JsonProperty( "lastName" ) String lastName ) {
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

public void test() {
    PersonMapper mapper = GWT.create( PersonMapper.class );

    String json = mapper.write( new Person( "John", "Doe" ) );
    System.out.println( json ); // > {"firstName":"John","lastName":"Doe"}

    Person person = mapper.read( json );
    System.out.println( person.getFirstName() + " " + person.getLastName() ); // > John Doe
}
```

Documentation
-------------
You can find documentation on the [wiki](https://github.com/nmorel/gwt-jackson/wiki).

Maven
-------------

```xml
<dependency>
  <groupId>com.github.nmorel.gwtjackson</groupId>
  <artifactId>gwt-jackson</artifactId>
  <version>0.1.0</version>
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

