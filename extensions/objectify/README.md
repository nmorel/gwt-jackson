gwt-jackson-objectify
=====
This extension contains serializer and deserializer for Objectify types like Ref or Key.

To use it add the library to your classpath (for maven, the artifactId is `gwt-jackson-objectify`):
1. Client-side: add `<inherits name="com.github.nmorel.gwtjackson.objectify.GwtJacksonObjectify" />` to your module descriptor XML file.
2. Server-side: register the Jackson module `ObjectifyJacksonModule`

Full working demo project: [gwt-storage-objectify](https://github.com/freddyboucher/gwt-storage-objectify)

Important note: Ref\<T\> is serialized as DeadRef\<T\> and the T value is serialized if it is loaded into the session.


Copyright and license
-------------

Copyright 2013 Nicolas Morel under the [Apache 2.0 license](LICENSE).
