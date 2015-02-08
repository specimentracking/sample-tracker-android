package org.galaxyproject.sampletracker.logic.preference;

/**
 * Preferences for user profile data.
 *
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public enum UserPreference implements PreferenceEnum {

    LAST_LOCATION_USED,
    LAST_FAMILY_USED,
    TOKEN;

    @Override
    public String getPreferencesName() {
        return "us";
    }
}
