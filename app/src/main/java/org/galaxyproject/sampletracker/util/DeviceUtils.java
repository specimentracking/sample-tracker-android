package org.galaxyproject.sampletracker.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings.Secure;
import android.view.inputmethod.EditorInfo;

import org.galaxyproject.sampletracker.GalaxyApplication;

import java.util.Locale;

/**
 * Utility methods that provides device specific info.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class DeviceUtils {

    private DeviceUtils() {
    }

    private static GalaxyApplication app() {
        return GalaxyApplication.get();
    }

    /**
     * Checks whether given Editor Action ID means the given action. For HTC devices (means probably with Sense X.0) we
     * accept also unspecified action.
     */
    public static boolean actionsEquals(int referenceActionId, int unknownActionId) {
        if (unknownActionId == referenceActionId) {
            return true;
        }
        // Exception, HTC does not implement IME types
        if (isHtc() && unknownActionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            return true;
        }
        return false;
    }

    /**
     * Identifies if the device is manufactured by HTC
     */
    private static boolean isHtc() {
        return Build.MODEL != null && Build.MODEL.toLowerCase(Locale.US).contains("htc");
    }

    /**
     * Checks current locale settings to select a language which should be used for communication with MCI.
     * 
     * @return Code of language to use for MCI communication
     */
    public static final String getMciLanguageCode() {
        String lowerLanguage = Locale.getDefault().getLanguage().toLowerCase(Locale.US);
        if (lowerLanguage.startsWith("cs") || lowerLanguage.startsWith("sk")) {
            return "cs";
        } else {
            return "en";
        }
    }

    public static String getAndroidId() {
        return Secure.getString(app().getContentResolver(), Secure.ANDROID_ID);
    }

    public static String getSerialNumber() {
        return Build.SERIAL;
    }

    /**
     * Returns true if we are connected to any network. If we don't have connection it returns false.
     * 
     * @return true whether we are connected to any network
     */
    public static boolean isAnyNetworkConnected() {
        return isMobileConnected() || isWiFiConnected();
    }

    /**
     * Is the device connected to Mobile network?
     * 
     * @return true if the device is connected to Mobile network, false otherwise.
     */
    public static boolean isMobileConnected() {
        NetworkInfo mobNetInfo = getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobNetInfo != null && mobNetInfo.isConnected();
    }

    /**
     * Is the device connected to Wi-Fi?
     * 
     * @return true if the device is connected to Wi-Fi, false otherwise.
     */
    public static boolean isWiFiConnected() {
        NetworkInfo wifiNetInfo = getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetInfo != null && wifiNetInfo.isConnected();
    }

    /**
     * Helper method for getting the NetworkInfo object of the current network.
     * 
     * @param type The type of the network
     * @return The NetworkInfo object.
     */
    private static NetworkInfo getNetworkInfo(int type) {
        ConnectivityManager manager = (ConnectivityManager) app().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getNetworkInfo(type);
    }
}
