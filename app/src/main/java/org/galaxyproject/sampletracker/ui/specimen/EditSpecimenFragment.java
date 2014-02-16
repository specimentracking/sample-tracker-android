package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;

/**
 * Fragment for creating editing a specimen that already exists on server.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class EditSpecimenFragment extends AbstractSpecimenFragment {

    public static AbstractSpecimenFragment create(Specimen specimen) {
        return create(new EditSpecimenFragment(), specimen);
    }

    @Inject private SpecimenResourceController mSpecimenController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_specimen_edit, container, false);
    }

    @Override
    protected boolean isModelValid(Specimen specimen) {
        // Only state or location may change
        SampleData sampleData = specimen.getSampleData();
        SpecimenLocation location = sampleData.getLocation();
        String state = sampleData.getState();
        if (location == null || state == null) {
            return false;
        }

        // Values must change
        SampleData originalSampleData = getOriginalSpecimen().getSampleData();
        SpecimenLocation originalLocation = originalSampleData.getLocation();
        String originalState = originalSampleData.getState();

        return !location.equals(originalLocation) || !state.equals(originalState);
    }

    @Override
    protected void sendModel(Specimen specimen) {
        // TODO
    }
}
