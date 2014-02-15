package org.galaxyproject.sampletracker.logic.settings;

import android.text.TextUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.galaxyproject.sampletracker.Environment;
import org.galaxyproject.sampletracker.logic.preference.GalaxyPreference;
import org.galaxyproject.sampletracker.logic.preference.PreferenceController;
import org.galaxyproject.sampletracker.logic.preference.UserPreference;
import org.galaxyproject.sampletracker.logic.security.Crypto;
import org.galaxyproject.sampletracker.logic.security.CryptoException;
import org.galaxyproject.sampletracker.logic.security.SecurityConfig;

import roboguice.util.Ln;

import javax.annotation.Nullable;

/**
 * Provides an access to all app settings and handles their encryption and safe storage.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@Singleton
public final class SettingsController {

    @Inject private PreferenceController mPreferenceController;

    public void storeApiKey(String apiKey) {
        String encryptedApiKey = encrypt(apiKey);
        mPreferenceController.putString(UserPreference.TOKEN, encryptedApiKey);
    }

    @Nullable
    public String loadApiKey() {
        String encryptedApiKey = mPreferenceController.getString(UserPreference.TOKEN, null);
        return encryptedApiKey == null ? null : decrypt(encryptedApiKey);
    }

    public void storeProjectId(String projectId) {
        String encryptedProjectId = encrypt(projectId);
        mPreferenceController.putString(GalaxyPreference.PROJECT_ID, encryptedProjectId);
    }

    @Nullable
    public String loadProjectId() {
        String encryptedProjectId = mPreferenceController.getString(GalaxyPreference.PROJECT_ID, null);
        return encryptedProjectId == null ? null : decrypt(encryptedProjectId);
    }

    public void storeServerUrl(String url) {
        String encryptedUrl = encrypt(url);
        mPreferenceController.putString(GalaxyPreference.SERVER_URL, encryptedUrl);
    }

    @Nullable
    public String loadServerUrl() {
        String encryptedUrl = mPreferenceController.getString(GalaxyPreference.SERVER_URL, null);

        // URL from app config is default
        if (TextUtils.isEmpty(encryptedUrl)) {
            return Environment.GALAXY_URL;
        } else {
            return decrypt(encryptedUrl);
        }
    }

    public void storeSettings(String apiKey, String projectId, String serverUrl) {
        storeApiKey(apiKey);
        storeProjectId(projectId);
        storeServerUrl(serverUrl);
    }

    @Nullable
    private String encrypt(String value) {
        try {
            return Crypto.encryptAes(SecurityConfig.getEncryptionKey(), value);
        } catch (CryptoException e) {
            Ln.w(e, "Error encrypting settings value");
            return null;
        }
    }

    private String decrypt(String encryptedValue) {
        try {
            return Crypto.decryptAes(SecurityConfig.getEncryptionKey(), encryptedValue);
        } catch (CryptoException e) {
            Ln.w(e, "Error decrypting settings value");
            return null;
        }
    }
}
