package org.galaxyproject.sampletracker.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

import org.galaxyproject.sampletracker.GalaxyApplication;
import org.galaxyproject.sampletracker.R;
import org.galaxyproject.sampletracker.logic.galaxy.AuthenticateResourceController;
import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;
import org.galaxyproject.sampletracker.model.galaxy.authenticate.AuthenticateResponse;
import org.galaxyproject.sampletracker.ui.component.EmptyTextWatcher;
import org.galaxyproject.sampletracker.ui.component.PendingDialogFragment;
import org.galaxyproject.sampletracker.ui.core.BaseActivity;
import org.galaxyproject.sampletracker.util.Toasts;

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

    public static final String EXTRA_API_KEY = "api_key";

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
        mUsernameField.addTextChangedListener(validatingTextWatcher());
        mPasswordField.addTextChangedListener(validatingTextWatcher());
    }

    private EmptyTextWatcher validatingTextWatcher() {
        return new EmptyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                checkFormValidity();
            }
        };
    }

    private void checkFormValidity() {
        boolean usernameOk = !TextUtils.isEmpty(mUsernameField.getText());
        boolean passwordOk = !TextUtils.isEmpty(mPasswordField.getText());
        mFetchButton.setEnabled(usernameOk && passwordOk);
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
    public void success(AuthenticateResponse userAuthentication, Response response) {
        PendingDialogFragment.hidePendingDialog(getFragmentManager());

        // Validate response for required fields
        String apiKey = userAuthentication.getKey();
        if (TextUtils.isEmpty(apiKey)) {
            Toasts.showLong(R.string.net_error_incomplete_response);
        } else {
            setResult(RESULT_OK, new Intent().putExtra(EXTRA_API_KEY, apiKey));
            finish();
        }
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
