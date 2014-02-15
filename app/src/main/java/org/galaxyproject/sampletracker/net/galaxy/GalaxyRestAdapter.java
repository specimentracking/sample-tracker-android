package org.galaxyproject.sampletracker.net.galaxy;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.BuildConfig;
import org.galaxyproject.sampletracker.logic.settings.SettingsController;

import retrofit.RestAdapter;

/**
 * Wrapper around Retrofit {@link RestAdapter} that handles Galaxy specific configuration.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@Singleton
public final class GalaxyRestAdapter {

    @Inject private SettingsController mSettingsController;

    private RestAdapter mAdapter;

    /**
     * Creates an API resource implementation defined by the specified interface.
     */
    public <T> T createResource(Class<T> service) {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        return mAdapter.create(service);
    }

    /**
     * Reset {@link GalaxyRestAdapter} so it is re-created with current settings.
     */
    public void reset() {
        mAdapter = null;
    }

    private RestAdapter createAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder();

        builder.setServer(mSettingsController.loadServerUrl());
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
