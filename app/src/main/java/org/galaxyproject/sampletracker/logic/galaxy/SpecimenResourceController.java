package org.galaxyproject.sampletracker.logic.galaxy;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.logic.settings.SettingsController;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
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

    public void get(String specimenId, Callback<Specimen> callback) {
        String apiKey = mSettingsController.loadApiKey();
        resource().get(apiKey, specimenId, callback);
    }

    public void check(String barcode, Callback<Specimen> callback) {
        String apiKey = mSettingsController.loadApiKey();
        String projectId = mSettingsController.loadProjectId();
        resource().check(apiKey, projectId, barcode, callback);
    }

    public void create(Specimen specimen, Callback<Specimen> callback) {
        String apiKey = mSettingsController.loadApiKey();
        String projectId = mSettingsController.loadProjectId();
        SampleData sampleData = specimen.getSampleData();
        resource().create(apiKey, projectId, specimen.getBarcode(), sampleData.getParentId(), sampleData.getState(),
                sampleData.getTypeFormatted(), sampleData.getLocationFormatted(), callback);
    }

    public void update(Specimen specimen, Callback<Specimen> callback) {
        String apiKey = mSettingsController.loadApiKey();
        String projectId = mSettingsController.loadProjectId();
        SampleData sampleData = specimen.getSampleData();
        resource().update(apiKey, projectId, specimen.getId(), sampleData.getState(), sampleData.getTypeFormatted(),
                sampleData.getLocationFormatted(), callback);
    }
}
