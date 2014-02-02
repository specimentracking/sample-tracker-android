package org.galaxyproject.sampletracker.ui;

import android.view.View;

import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;
import org.galaxyproject.sampletracker.ui.scan.ScanActivity;
import org.galaxyproject.sampletracker.ui.settings.SettingsActivity;

import roboguice.inject.ContentView;
import roboguice.util.Ln;

/**
 * Landing activity with main menu.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_main)
public final class MainActivity extends BaseActivity {

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings:
                startActivity(SettingsActivity.showIntent());
                break;
            case R.id.scan:
                startActivity(ScanActivity.showIntent());
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }
}
