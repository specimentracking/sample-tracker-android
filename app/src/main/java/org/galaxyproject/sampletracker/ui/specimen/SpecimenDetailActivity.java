package org.galaxyproject.sampletracker.ui.specimen;

import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenResponse;
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
public final class SpecimenDetailActivity extends BaseActivity implements Callback<SpecimenResponse> {

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
    public void success(SpecimenResponse specimenResponse, Response response) {
        // TODO
        Toasts.showShort("Success " + specimenResponse.getSpecimen());
    }

    @Override
    public void failure(RetrofitError error) {
        // TODO - 404 json only
        Ln.w(error);
        Toasts.showShort("Error " + error.getResponse().getStatus());
    }
}
