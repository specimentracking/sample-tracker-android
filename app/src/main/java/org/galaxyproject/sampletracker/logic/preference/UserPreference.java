package org.galaxyproject.sampletracker.logic.preference;

/**
 * Preferences for user profile data.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public enum UserPreference implements PreferenceEnum {

    TOKEN;

    @Override
    public String getPreferencesName() {
        return "us";
    }
}
