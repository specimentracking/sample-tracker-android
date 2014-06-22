package org.galaxyproject.sampletracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.BuildConfig;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.settings.SettingsController;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;
import org.galaxyproject.sampletracker.ui.scan.ScanActivity;
import org.galaxyproject.sampletracker.ui.settings.SettingsActivity;
import org.galaxyproject.sampletracker.ui.specimen.SpecimenDetailActivity;
import org.galaxyproject.sampletracker.util.Toasts;
import org.galaxyproject.sampletracker.util.ViewUtils;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Landing activity with main menu.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_main)
public final class MainActivity extends BaseActivity {

    @Inject private SettingsController mSettingsController;
    @InjectView(R.id.debug_panel) private View mDebugPanel;
    @InjectView(R.id.simulated_barcode) private EditText mSimulatedBarcodeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Barcode simulation panel for debug purposes
        ViewUtils.conditionalGone(BuildConfig.DEBUG, mDebugPanel);
        if (BuildConfig.DEBUG) {
            mSimulatedBarcodeField.setText("450645065");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case R.id.request_scan:
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(SpecimenDetailActivity.queryBarcodeIntent(data.getStringExtra(ScanActivity.EXTRA_SCAN_DATA)));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings:
                startActivity(SettingsActivity.showIntent());
                break;
            case R.id.scan:
                if (mSettingsController.settingsAreValid()) {
                    startActivityForResult(ScanActivity.showIntent(), R.id.request_scan);
                } else {
                    Toasts.showLong(R.string.scan_error_invalid_settings);
                }
                break;
            case R.id.simulate_scan:
                startActivity(SpecimenDetailActivity.queryBarcodeIntent(mSimulatedBarcodeField.getText().toString()));
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }
}
