gwt-jackson-remotelogging
=====
This extension contains serializer for Throwable.

It allows you to support the equivalent of GWT RPC Remote Logging http://www.gwtproject.org/doc/latest/DevGuideLogging.html#Remote_Logging

To use it add the library to your classpath (for maven, the artifactId is `gwt-jackson-remotelogging`):
1. Client-side: add `<inherits name="com.github.nmorel.gwtjackson.remotelogging.GwtJacksonRemoteLogging" />` to your module descriptor XML file.
2. Server-side: register the Jackson module `RemoteLoggingJacksonModule`

Full working demo project: [gwt-storage-objectify](https://github.com/freddyboucher/gwt-storage-objectify)


Copyright and license
-------------

Copyright 2013 Nicolas Morel under the [Apache 2.0 license](LICENSE).
