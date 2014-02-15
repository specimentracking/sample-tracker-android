package org.galaxyproject.sampletracker.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import org.galaxyproject.sampletracker.BuildConfig;
import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.settings.SettingsController;
import org.galaxyproject.sampletracker.net.galaxy.GalaxyRestAdapter;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;
import org.galaxyproject.sampletracker.util.Toasts;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Allows to modify user and project settings.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_settings)
public final class SettingsActivity extends BaseActivity implements OnClickListener, TextWatcher {

    public static final Intent showIntent() {
        return new Intent(GalaxyApplication.get(), SettingsActivity.class);
    }

    @Inject private SettingsController mSettingsController;
    @Inject private GalaxyRestAdapter mGalaxyRestAdapter;
    @InjectView(R.id.key) private EditText mKeyField;
    @InjectView(R.id.project_id) private EditText mProjectIdField;
    @InjectView(R.id.server_url) private EditText mServerUrlField;
    @InjectView(R.id.save) private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();

        mKeyField.addTextChangedListener(this);
        mProjectIdField.addTextChangedListener(this);
        mServerUrlField.addTextChangedListener(this);
        mSaveButton.setOnClickListener(this);

        mKeyField.setText(mSettingsController.loadApiKey());
        mProjectIdField.setText(mSettingsController.loadProjectId());
        mServerUrlField.setText(mSettingsController.loadServerUrl());

        if (BuildConfig.DEBUG) {
            if (TextUtils.isEmpty(mKeyField.getText())) {
                mKeyField.setText("b25cb96a82668561e861c3238305dc8a");
            }
            if (TextUtils.isEmpty(mProjectIdField.getText())) {
                mProjectIdField.setText("f2db41e1fa331b3e");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mSaveButton.setEnabled(enteredDataAreValid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                doSave();
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }

    private boolean enteredDataAreValid() {
        boolean keyOk = !TextUtils.isEmpty(mKeyField.getText());
        boolean projectIdOk = !TextUtils.isEmpty(mProjectIdField.getText());
        boolean serverUrlOk = !TextUtils.isEmpty(mServerUrlField.getText());
        return keyOk && projectIdOk && serverUrlOk;
    }

    private void doSave() {
        Preconditions.checkState(enteredDataAreValid());

        // Store settings data
        String key = mKeyField.getText().toString();
        String projectId = mProjectIdField.getText().toString();
        String serverUrl = mServerUrlField.getText().toString();
        mSettingsController.storeSettings(key, projectId, serverUrl);

        // Recreate rest adapter to reflect potential server URL change
        mGalaxyRestAdapter.reset();

        // Inform user and go back
        Toasts.showShort(R.string.settings_saved_msg);
        finish();
    }
}
