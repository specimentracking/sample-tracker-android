package org.galaxyproject.sampletracker.net.galaxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.BuildConfig;
import org.galaxyproject.sampletracker.logic.settings.SettingsController;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.net.galaxy.adapter.DateDeserializer;
import org.galaxyproject.sampletracker.net.galaxy.adapter.SpecimenLocationDeserializer;
import org.galaxyproject.sampletracker.net.galaxy.adapter.SpecimenTypeDeserializer;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.util.Date;

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
        builder.setConverter(new GsonConverter(initGson()));

        return builder.build();
    }

    private static Gson initGson() {
        return new GsonBuilder() //
                .registerTypeAdapter(Date.class, new DateDeserializer()) //
                .registerTypeAdapter(SpecimenLocation.class, new SpecimenLocationDeserializer()) //
                .registerTypeAdapter(SpecimenType.class, new SpecimenTypeDeserializer()) //
                .create();
    }

    private static RestAdapter.LogLevel logLevel() {
        if (BuildConfig.LOG_NETWORK) {
            return RestAdapter.LogLevel.FULL;
        } else {
            return RestAdapter.LogLevel.NONE;
        }
    }
}
