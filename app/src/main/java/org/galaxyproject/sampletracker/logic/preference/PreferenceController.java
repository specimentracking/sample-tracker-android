package org.galaxyproject.sampletracker.logic.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Set;

/**
 * Utility ancestor for all shared preferences used in application. Provides access methods for common java types
 * storage. It operates only with enums implementing {@link PreferenceEnum} interface.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
@Singleton
public final class PreferenceController {

    @Inject private Application mApplication;

    public PreferenceController() {
        preset();
    }

    /**
     * Called when {@link PreferenceController} is used first to preset some default preference values if needed.
     */
    private void preset() {
        // Some preference initialization may be called here
    }

    private SharedPreferences get(String preferenceName) {
        return mApplication.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    /**
     * Checked whether given preference is any value set in preferences.
     * 
     * @param preference Preference key that is to be checked
     * @return Flag indicating whether given preference has been already set
     */
    public <T extends Enum<T> & PreferenceEnum> boolean contains(T preference) {
        return get(preference.getPreferencesName()).contains(preference.name());
    }

    /**
     * Removes given preference from preferences.
     * 
     * @param preference Preference key that is to be removed
     */
    public <T extends Enum<T> & PreferenceEnum> void remove(T preference) {
        get(preference.getPreferencesName()).edit().remove(preference.name()).commit();
    }

    /**
     * Returns the boolean value of given preference or <code>false</code> if not set.
     * 
     * @param preference Preference key of the value
     * @return boolean value of given preference or <code>false</code> if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> boolean getBoolean(T preference) {
        return getBoolean(preference, false);
    }

    /**
     * Returns the boolean value of given preference or specified default value if not set.
     * 
     * @param preference Preference key of the value
     * @param defValue Value that will be returned if not set
     * @return boolean value of given preference or default value if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> boolean getBoolean(T preference, boolean defValue) {
        return get(preference.getPreferencesName()).getBoolean(preference.name(), defValue);
    }

    /**
     * Writes the value to default shared preferences under the preference key.
     * 
     * @param preference Preference key of the value
     * @param value Value to be set under the preference key
     */
    public <T extends Enum<T> & PreferenceEnum> void putBoolean(T preference, boolean value) {
        get(preference.getPreferencesName()).edit().putBoolean(preference.name(), value).commit();
    }

    /**
     * Returns the int value of given preference or specified default value if not set.
     * 
     * @param preference Preference key of the value
     * @param defValue Value that will be returned if not set
     * @return int value of given preference or <code>defValue</code> if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> int getInt(T preference, int defValue) {
        return get(preference.getPreferencesName()).getInt(preference.name(), defValue);
    }

    /**
     * Writes the value to default shared preferences under the preference key.
     * 
     * @param preference Preference key of the value
     * @param value Value to be set under the preference key
     */
    public <T extends Enum<T> & PreferenceEnum> void putInt(T preference, int value) {
        get(preference.getPreferencesName()).edit().putInt(preference.name(), value).commit();
    }

    /**
     * Returns the long value of given preference or defValue if not set.
     * 
     * @param preference Preference key of the value
     * @param defValue Value that will be returned if not set
     * @return long value of given preference or <code>defValue</code> if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> long getLong(T preference, long defValue) {
        return get(preference.getPreferencesName()).getLong(preference.name(), defValue);
    }

    /**
     * Writes the value to default shared preferences under the preference key.
     * 
     * @param preference Preference key of the value
     * @param value Value to be set under the preference key
     */
    public <T extends Enum<T> & PreferenceEnum> void putLong(T preference, long value) {
        get(preference.getPreferencesName()).edit().putLong(preference.name(), value).commit();
    }

    /**
     * Returns the float value of given preference or defValue if not set.
     * 
     * @param preference Preference key of the value
     * @param defValue Value that will be returned if not set
     * @return float value of given preference or <code>defValue</code> if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> float getFloat(T preference, float defValue) {
        return get(preference.getPreferencesName()).getFloat(preference.name(), defValue);
    }

    /**
     * Writes the value to default shared preferences under the preference key.
     * 
     * @param preference Preference key of the value
     * @param value Value to be set under the preference key
     */
    public <T extends Enum<T> & PreferenceEnum> void putFloat(T preference, float value) {
        get(preference.getPreferencesName()).edit().putFloat(preference.name(), value).commit();
    }

    /**
     * Returns the String value of given preference or specified default value if not set.
     * 
     * @param preference Preference key of the value
     * @param defValue Value that will be returned if not set
     * @return String value of given preference or <code>defValue</code> if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> String getString(T preference, String defValue) {
        return get(preference.getPreferencesName()).getString(preference.name(), defValue);
    }

    /**
     * Writes the value to default shared preferences under the preference key.
     * 
     * @param preference Preference key of the value
     * @param value Value to be set under the preference key
     */
    public <T extends Enum<T> & PreferenceEnum> void putString(T preference, String value) {
        get(preference.getPreferencesName()).edit().putString(preference.name(), value).commit();
    }

    /**
     * Returns the String set value of given preference or <code>defValue</code> if not set.
     * 
     * @param preference Preference key of the value
     * @param defValue Value that will be returned if not set
     * @return String set value of given preference or <code>defValue</code> if not set.
     */
    public <T extends Enum<T> & PreferenceEnum> Set<String> getStringSet(T preference, Set<String> defValue) {
        return get(preference.getPreferencesName()).getStringSet(preference.name(), defValue);
    }

    /**
     * Writes the value to default shared preferences under the preference key.
     * 
     * @param preference Preference key of the value
     * @param value Value to be set under the preference key
     */
    public <T extends Enum<T> & PreferenceEnum> void putStringSet(T preference, Set<String> value) {
        get(preference.getPreferencesName()).edit().putStringSet(preference.name(), value).commit();
    }
}
