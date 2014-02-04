package org.galaxyproject.sampletracker.ui.specimen;

import android.content.Intent;
import android.os.Bundle;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;

/**
 * Detail of single specimen, either existing, new or derivative.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_specimen_detail)
public final class SpecimenDetailActivity extends BaseActivity {

    /** Barcode value of the specimen */
    private static final String EXTRA_BARCODE = "barcode";

    public static final Intent showIntent(String barcode) {
        Intent intent = new Intent(GalaxyApplication.get(), SpecimenDetailActivity.class);
        intent.putExtra(EXTRA_BARCODE, barcode);
        return intent;
    }

    @InjectExtra(EXTRA_BARCODE) private String mBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();
    }

    @Override
    public void onBackPressed() {
        upToParent();
    }
}
