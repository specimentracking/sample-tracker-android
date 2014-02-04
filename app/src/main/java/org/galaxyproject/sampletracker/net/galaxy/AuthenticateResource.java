package org.galaxyproject.sampletracker.net.galaxy;

import org.galaxyproject.sampletracker.model.galaxy.authenticate.AuthenticateResponse;

import retrofit.RestAdapter;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * {@link RestAdapter} resource for authentication.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public interface AuthenticateResource {

    @POST("/api/authenticate/baseauth")
    public AuthenticateResponse authenticate(@Header("Authorization") String authorization);
}
