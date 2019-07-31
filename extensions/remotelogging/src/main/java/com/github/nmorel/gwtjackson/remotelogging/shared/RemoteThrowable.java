/*
 * Copyright 2018 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.remotelogging.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( {"localizedMessage", "suppressed"} )
public final class RemoteThrowable extends Throwable {

    private final String remoteClassName;

    public RemoteThrowable( Throwable throwable ) {
        super( throwable.getMessage(), throwable.getCause() != null ? new RemoteThrowable( throwable.getCause() ) : null );
        setStackTrace( throwable.getStackTrace() );
        remoteClassName = throwable.getClass().getName();
    }

    @JsonCreator
    public RemoteThrowable( @JsonProperty( "message" ) String message, @JsonProperty( "cause" ) RemoteThrowable cause, @JsonProperty(
            "remoteClassName" ) String remoteClassName ) {
        super( message, cause );
        this.remoteClassName = remoteClassName;
    }

    public String getRemoteClassName() {
        return remoteClassName;
    }

    @Override
    public synchronized RemoteThrowable getCause() {
        return (RemoteThrowable) super.getCause();
    }

    @Override
    public String toString() {
        String message = getMessage();
        return (message != null) ? (remoteClassName + ": " + message) : remoteClassName;
    }
}
