package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;
import org.galaxyproject.sampletracker.util.Toasts;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.util.Ln;

/**
 * Fragment for creating editing a specimen that already exists on server.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class EditSpecimenFragment extends AbstractSpecimenFragment implements Callback<Specimen> {

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
        SpecimenType type = sampleData.getType();
        String state = sampleData.getState();
        if (location == null || type == null || state == null) {
            return false;
        }

        // Values must change
        SampleData originalSampleData = getOriginalSpecimen().getSampleData();
        SpecimenLocation originalLocation = originalSampleData.getLocation();
        SpecimenType originalType = originalSampleData.getType();
        String originalState = originalSampleData.getState();

        return !location.equals(originalLocation) || !type.equals(originalType) || !state.equals(originalState);
    }

    @Override
    protected void sendModel(Specimen specimen) {
        mSpecimenController.update(specimen, this);
    }

    @Override
    public void success(Specimen specimen, Response response) {
        Toasts.showLong(R.string.net_specimen_updated);
        getActivity().onBackPressed();
    }

    @Override
    public void failure(RetrofitError error) {
        try {
            if (error.getBody() instanceof AbstractResponse) {
                Toasts.showLong(((AbstractResponse) error.getBody()).getErrorMessage());
            } else {
                throw new IllegalStateException("Invalid response received");
            }
        } catch (Exception e) {
            Ln.w(error, "Unexpected error");
            String msg = error.getMessage();
            if (TextUtils.isEmpty(msg)) {
                Toasts.showLong(R.string.net_error_unknown);
            } else {
                Toasts.showLong(msg);
            }
        }
    }
}
