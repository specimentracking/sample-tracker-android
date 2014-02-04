package org.galaxyproject.sampletracker.model.galaxy.authenticate;

import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;

/**
 * Model of response to an authentication request.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class AuthenticateResponse extends AbstractResponse {

    private String key;

    public String getKey() {
        return key;
    }
}
