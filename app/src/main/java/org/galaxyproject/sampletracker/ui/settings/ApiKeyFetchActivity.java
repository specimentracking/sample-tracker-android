package org.galaxyproject.sampletracker.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.AuthenticateResourceController;
import org.galaxyproject.sampletracker.model.galaxy.authenticate.AuthenticateResponse;
import org.galaxyproject.sampletracker.ui.component.PendingDialogFragment;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

/**
 * Fetches an API key automatically based on username a password.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@ContentView(R.layout.act_api_key_fetch)
public final class ApiKeyFetchActivity extends BaseActivity implements View.OnClickListener, Callback<AuthenticateResponse> {

    public static final Intent showIntent() {
        return new Intent(GalaxyApplication.get(), ApiKeyFetchActivity.class);
    }

    private static final int CONTENT = android.R.id.content;

    @Inject private AuthenticateResourceController mAuthenticateResourceController;
    @InjectView(R.id.username) private EditText mUsernameField;
    @InjectView(R.id.password) private EditText mPasswordField;
    @InjectView(R.id.fetch) private Button mFetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton();
        mFetchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fetch:
                PendingDialogFragment.showPendingDialog(getFragmentManager(), CONTENT);
                String username = mUsernameField.getText().toString();
                String password = mPasswordField.getText().toString();
                mAuthenticateResourceController.authenticate(username, password, this);
                break;
            default:
                Ln.w("Unknown view has been clicked");
        }
    }

    @Override
    public void success(AuthenticateResponse reponse, Response response) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());
        // TODO
    }

    @Override
    public void failure(RetrofitError error) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());
        // TODO
    }
}
