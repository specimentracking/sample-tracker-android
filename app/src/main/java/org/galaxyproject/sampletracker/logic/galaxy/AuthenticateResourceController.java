package org.galaxyproject.sampletracker.logic.galaxy;

import android.util.Base64;

import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.model.galaxy.authenticate.AuthenticateResponse;
import org.galaxyproject.sampletracker.net.galaxy.resource.AuthenticateResource;

import retrofit.Callback;
import roboguice.util.Ln;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Handles user authentication process.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@Singleton
public final class AuthenticateResourceController extends ResourceController<AuthenticateResource> {

    public AuthenticateResourceController() {
        super(AuthenticateResource.class);
    }

    public void authenticate(String username, String password, Callback<AuthenticateResponse> callback) {
        String basicAuthentication = createBasicAuthentication(username, password);
        resource().authenticate(basicAuthentication, callback);
    }

    private String createBasicAuthentication(String username, String password) {
        try {
            String plainValue = String.format(Locale.US, "%s:%s", username, password);
            String encodedValue = Base64.encodeToString(plainValue.getBytes("UTF-8"), Base64.NO_WRAP);
            return String.format(Locale.US, "Basic %s", encodedValue);
        } catch (UnsupportedEncodingException e) {
            Ln.e(e, "Error creating signature");
            return null;
        }
    }
}
