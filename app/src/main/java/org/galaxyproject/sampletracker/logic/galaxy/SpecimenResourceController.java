package org.galaxyproject.sampletracker.logic.galaxy;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.logic.settings.SettingsController;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenResponse;
import org.galaxyproject.sampletracker.net.galaxy.GalaxyRestAdapter;
import org.galaxyproject.sampletracker.net.galaxy.SpecimenResource;

import retrofit.Callback;

/**
 * Handles specimen data.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@Singleton
public final class SpecimenResourceController {

    @Inject private SettingsController mSettingsController;

    private final SpecimenResource mResource = GalaxyRestAdapter.createResource(SpecimenResource.class);

    public void check(String barcode, Callback<SpecimenResponse> callback) {
        String apiKey = mSettingsController.loadApiKey();
        String projectId = mSettingsController.loadProjectId();
        mResource.check(apiKey, projectId, barcode, callback);
    }
}
