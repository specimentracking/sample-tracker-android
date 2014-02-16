package org.galaxyproject.sampletracker.logic.galaxy;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.logic.settings.SettingsController;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.net.galaxy.resource.SpecimenResource;

import retrofit.Callback;

/**
 * Handles specimen data.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@Singleton
public final class SpecimenResourceController extends ResourceController<SpecimenResource> {

    @Inject private SettingsController mSettingsController;

    public SpecimenResourceController() {
        super(SpecimenResource.class);
    }

    public void check(String barcode, Callback<Specimen> callback) {
        String apiKey = mSettingsController.loadApiKey();
        String projectId = mSettingsController.loadProjectId();
        resource().check(apiKey, projectId, barcode, callback);
    }
}
