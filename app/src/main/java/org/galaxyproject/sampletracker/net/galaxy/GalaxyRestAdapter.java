package org.galaxyproject.sampletracker.net.galaxy;

import org.galaxyproject.sampletracker.BuildConfig;
import org.galaxyproject.sampletracker.Environment;

import retrofit.RestAdapter;

/**
 * Wrapper around Retrofit {@link RestAdapter} that handles Galaxy specific configuration.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class GalaxyRestAdapter {

    private static final RestAdapter sAdapter = createAdapter();

    /**
     * Creates an API resource implementation defined by the specified interface.
     */
    public static <T> T createResource(Class<T> service) {
        return sAdapter.create(service);
    }

    private static RestAdapter createAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder();

        builder.setServer(Environment.GALAXY_URL);
        builder.setLogLevel(logLevel());
        builder.setClient(new GalaxyClient());

        return builder.build();
    }

    private static RestAdapter.LogLevel logLevel() {
        if (BuildConfig.LOG_NETWORK) {
            return RestAdapter.LogLevel.FULL;
        } else {
            return RestAdapter.LogLevel.NONE;
        }
    }
}
