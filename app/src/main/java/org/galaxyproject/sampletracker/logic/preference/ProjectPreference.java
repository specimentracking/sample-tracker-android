package org.galaxyproject.sampletracker.logic.preference;

/**
 * Preferences for current project settings.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public enum ProjectPreference implements PreferenceEnum {

    PROJECT_ID;

    @Override
    public String getPreferencesName() {
        return "pr";
    }
}
