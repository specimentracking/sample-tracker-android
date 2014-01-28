package org.galaxyproject.sampletracker.ui.settings;

import android.content.Intent;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import roboguice.inject.ContentView;

/**
 * Allows to modify user and project settings.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_settings)
public final class SettingsActivity extends BaseActivity {

    public static final Intent showIntent() {
        return new Intent(GalaxyApplication.get(), SettingsActivity.class);
    }
}
