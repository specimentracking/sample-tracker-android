package org.galaxyproject.sampletracker.net.galaxy;

import com.squareup.okhttp.OkHttpClient;

import org.galaxyproject.sampletracker.Environment;
import org.galaxyproject.sampletracker.net.ssl.EasyHostnameVerifier;
import org.galaxyproject.sampletracker.net.ssl.EasySSLSocketFactory;

import retrofit.client.OkClient;

import java.util.concurrent.TimeUnit;

/**
 * Client for communication with Galaxy server.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class GalaxyClient extends OkClient {

    /** Network connection timeout */
    private static final long CONNECT_TIMEOUT_SECONDS = 60L;

    /** Network read timeout */
    private static final long READ_TIMEOUT_SECONDS = 60L;

    public GalaxyClient() {
        super(client());
    }

    /**
     * @return OK HTTP client with correct parameters set and ability to ignore SSL certificates if required.
     */
    private static OkHttpClient client() {
        OkHttpClient galaxyHttpClient = new OkHttpClient();

        // Set correct network timeouts
        galaxyHttpClient.setConnectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        galaxyHttpClient.setReadTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        // Disable SSL verification if required
        if (!Environment.GALAXY_SSL_VERIFY) {
            galaxyHttpClient.setHostnameVerifier(new EasyHostnameVerifier());
            galaxyHttpClient.setSslSocketFactory(new EasySSLSocketFactory());
        }

        return galaxyHttpClient;
    }

}
