package org.galaxyproject.sampletracker.ui.specimen;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.SpecimenResourceController;
import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;
import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;
import org.galaxyproject.sampletracker.ui.component.PendingDialogFragment;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;
import org.galaxyproject.sampletracker.util.Toasts;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.inject.InjectExtra;
import roboguice.util.Ln;

/**
 * Detail of single specimen, either existing or new.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenDetailActivity extends BaseActivity implements Callback<Specimen> {

    /** Unique ID value of the specimen for query - has priority before barcode */
    private static final String EXTRA_ID = "specimen_id";

    /** Barcode value of the specimen for query */
    private static final String EXTRA_BARCODE = "barcode";

    public static final Intent queryIdIntent(String specimenId) {
        Intent intent = new Intent(GalaxyApplication.get(), SpecimenDetailActivity.class);
        intent.putExtra(EXTRA_ID, specimenId);
        return intent;
    }

    public static final Intent queryBarcodeIntent(String barcode) {
        Intent intent = new Intent(GalaxyApplication.get(), SpecimenDetailActivity.class);
        intent.putExtra(EXTRA_BARCODE, barcode);
        return intent;
    }

    private static final int CONTENT = android.R.id.content;

    @InjectExtra(value = EXTRA_BARCODE, optional = true) private String mBarcode;
    @InjectExtra(value = EXTRA_ID, optional = true) private String mId;
    @Inject private SpecimenResourceController mSpecimenController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        Preconditions.checkArgument(!TextUtils.isEmpty(mId) || !TextUtils.isEmpty(mBarcode), "ID or barcode must be set");

        if (savedInstanceState == null) {
            PendingDialogFragment.showPendingDialog(getFragmentManager(), CONTENT);

            // Query based on ID has priority before barcode check
            if (!TextUtils.isEmpty(mId)) {
                mSpecimenController.get(mId, this);
            } else {
                mSpecimenController.check(mBarcode, this);
            }
        }
    }

    @Override
    public void success(Specimen specimen, Response response) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());

        // Validate response for required fields
        if (TextUtils.isEmpty(specimen.getId()) || TextUtils.isEmpty(specimen.getBarcode())) {
            Toasts.showLong(R.string.net_error_incomplete_response);
        } else {
            showContent(EditSpecimenFragment.create(specimen), R.string.specimen_title_update);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());

        // Check response content whether it is valid. HTTP 404 means non existing specimen, any other code means error.
        try {
            if (error.getBody() instanceof AbstractResponse) {
                if (error.getResponse().getStatus() == 404) {
                    showContent(CreateSpecimenFragment.createNew(mBarcode), R.string.specimen_title_create);
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

    private void showContent(Fragment fragment, int titleId) {
        setTitle(titleId);
        getFragmentManager().beginTransaction().replace(CONTENT, fragment).commit();
    }
}
