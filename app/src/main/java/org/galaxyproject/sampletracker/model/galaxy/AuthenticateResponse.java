package org.galaxyproject.sampletracker.model.galaxy;

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
