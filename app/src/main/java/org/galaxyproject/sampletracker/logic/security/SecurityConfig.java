package org.galaxyproject.sampletracker.logic.security;

import android.text.TextUtils;

import org.galaxyproject.sampletracker.BuildConfig;
import org.galaxyproject.sampletracker.util.DeviceUtils;

import roboguice.util.Ln;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Constants and methods that defines security rules.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SecurityConfig {

    private static final String CHARSET = "UTF-8";
    private static final String DEFAULT_ATTRIBUTE_VALUE = "00000000";

    /**
     * Creates and returns a key for encryption based on device attributes:
     * <ul>
     * <li>Android ID</li>
     * <li>Serial number</li>
     * </ul>
     * When any of attributes is empty, a default value is used.
     */
    public static String getEncryptionKey() {
        try {
            String attr1 = getAndroidId();
            String attr2 = getSerialNumber();

            String keyBase = String.format(Locale.US, "%s-%s", attr1, attr2);
            String encryptionKey = Crypto.hashSha512ToString(keyBase.getBytes(CHARSET));

            if (BuildConfig.DEBUG) {
                Ln.i("Encryption key: " + encryptionKey);
            }
            return encryptionKey;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getAndroidId() {
        String androidId = DeviceUtils.getAndroidId();
        if (TextUtils.isEmpty(androidId)) {
            androidId = DEFAULT_ATTRIBUTE_VALUE;
        }
        if (BuildConfig.DEBUG) {
            Ln.i("Android ID: " + androidId);
        }
        return androidId;
    }

    private static String getSerialNumber() {
        String serialNumber = DeviceUtils.getSerialNumber();
        if (TextUtils.isEmpty(serialNumber)) {
            serialNumber = DEFAULT_ATTRIBUTE_VALUE;
        }
        if (BuildConfig.DEBUG) {
            Ln.i("Serial number: " + serialNumber);
        }
        return serialNumber;
    }
}
