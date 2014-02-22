package org.galaxyproject.sampletracker.ui.specimen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SampleData;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.ui.component.PendingDialogFragment;
import org.galaxyproject.sampletracker.util.Toasts;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.util.Ln;

/**
 * Fragment for creating a completely new specimen based on scanned barcode or a derivative based on parent specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class CreateSpecimenFragment extends AbstractSpecimenFragment implements Callback<Specimen> {

    public static AbstractSpecimenFragment createNew(String barcode) {
        Preconditions.checkArgument(!TextUtils.isEmpty(barcode));
        return create(new CreateSpecimenFragment(), Specimen.from(barcode));
    }

    public static AbstractSpecimenFragment createDerivative(String barcode, String parentId) {
        Preconditions.checkArgument(!TextUtils.isEmpty(barcode));
        Preconditions.checkArgument(!TextUtils.isEmpty(parentId));
        return create(new CreateSpecimenFragment(), Specimen.from(barcode, parentId));
    }

    @Inject private SpecimenResourceController mSpecimenController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_specimen_create, container, false);
    }

    @Override
    protected boolean isModelValid(Specimen specimen) {
        SampleData sampleData = specimen.getSampleData();
        return sampleData.getState() != null;
    }

    @Override
    protected void sendModel(Specimen specimen) {
        PendingDialogFragment.showPendingDialog(getFragmentManager());
        mSpecimenController.create(specimen, this);
    }

    @Override
    public void success(Specimen specimen, Response response) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());
        Toasts.showLong(R.string.net_specimen_created);
        getActivity().onBackPressed();
    }

    @Override
    public void failure(RetrofitError error) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());
        try {
            if (error.getBody() instanceof AbstractResponse) {
                Toasts.showLong(((AbstractResponse) error.getBody()).getErrorMessage());
            } else {
                throw new IllegalStateException("Invalid response received");
            }
        } catch (Exception e) {
            Ln.w(error, "Unexpected error");
            Toasts.showLong(error.getMessage());
        }
    }
}
