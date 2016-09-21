package org.galaxyproject.sampletracker.model.galaxy.authenticate;

import com.google.gson.annotations.SerializedName;

import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;

/**
 * Model of response to an authentication request.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class AuthenticateResponse extends AbstractResponse {

    @SerializedName("api_key") private String key;

    public String getKey() {
        return key;
    }
}
