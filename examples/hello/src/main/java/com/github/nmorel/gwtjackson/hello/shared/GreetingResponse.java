package com.github.nmorel.gwtjackson.hello.shared;

public class GreetingResponse {

    private String greeting;

    private String serverInfo;

    private String userAgent;

    public GreetingResponse(){}

    public GreetingResponse( String greeting, String serverInfo, String userAgent ) {
        this.greeting = greeting;
        this.serverInfo = serverInfo;
        this.userAgent = userAgent;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public String getUserAgent() {
        return userAgent;
    }

}
