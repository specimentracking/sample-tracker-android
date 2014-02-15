package org.galaxyproject.sampletracker.ui.specimen;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;
import org.galaxyproject.sampletracker.util.Toasts;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.inject.InjectExtra;
import roboguice.util.Ln;

/**
 * Detail of single specimen, either existing, new or derivative.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenDetailActivity extends BaseActivity implements Callback<Specimen> {

    /** Barcode value of the specimen */
    private static final String EXTRA_BARCODE = "barcode";

    public static final Intent showIntent(String barcode) {
        Intent intent = new Intent(GalaxyApplication.get(), SpecimenDetailActivity.class);
        intent.putExtra(EXTRA_BARCODE, barcode);
        return intent;
    }

    @InjectExtra(EXTRA_BARCODE) private String mBarcode;
    @Inject private SpecimenResourceController mSpecimenController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        if (savedInstanceState == null) {
            mSpecimenController.check(mBarcode, this);
        }
    }

    @Override
    public void onBackPressed() {
        upToParent();
    }

    @Override
    public void success(Specimen specimen, Response response) {
        showContent(EditSpecimenFragment.create(specimen));
    }

    @Override
    public void failure(RetrofitError error) {
        // Check response content whether it is valid. HTTP 404 means non existing specimen, any other code means error.
        try {
            if (error.getBody() instanceof AbstractResponse) {
                if (error.getResponse().getStatus() == 404) {
                    showContent(CreateSpecimenFragment.createNew(mBarcode));
                } else {
                    Toasts.showLong(((AbstractResponse) error.getBody()).getErrorMessage());
                }
            } else {
                throw new IllegalStateException("Invalid response received");
            }
        } catch (Exception e) {
            Ln.w(error, "Unexpected error");
            Toasts.showLong(error.getMessage());
        }
    }

    private void showContent(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }
}
