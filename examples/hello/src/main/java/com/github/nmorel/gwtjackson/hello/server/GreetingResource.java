package com.github.nmorel.gwtjackson.hello.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nmorel.gwtjackson.hello.shared.FieldVerifier;
import com.github.nmorel.gwtjackson.hello.shared.GreetingRequest;
import com.github.nmorel.gwtjackson.hello.shared.GreetingResponse;

/**
 * @author Nicolas Morel
 */
public class GreetingResource extends HttpServlet {

    /**
     * Jackson ObjectMapper to desererialize the request and serialize the response
     */
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        // Deserialize the request
        GreetingRequest greetingRequest = mapper.readValue( req.getInputStream(), GreetingRequest.class );

        // Verify that the input is valid.
        if ( !FieldVerifier.isValidName( greetingRequest.getName() ) ) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new IllegalArgumentException( "Name must be at least 4 characters long" );
        }

        GreetingResponse response = new GreetingResponse();
        response.setServerInfo( getServletContext().getServerInfo() );
        response.setUserAgent( req.getHeader( "User-Agent" ) );
        response.setGreeting( "Hello, " + greetingRequest.getName() + "!" );

        // Serialize the response into the servlet output
        mapper.writeValue( resp.getOutputStream(), response );
    }
}
