package org.galaxyproject.sampletracker.logic.preference;

/**
 * Preferences for galaxy server settings.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public enum GalaxyPreference implements PreferenceEnum {

    /** Current Galaxy project ID */
    PROJECT_ID,

    /** Galaxy server URL preference that may overwrite setting from app configuration */
    SERVER_URL;

    @Override
    public String getPreferencesName() {
        return "gal";
    }
}
