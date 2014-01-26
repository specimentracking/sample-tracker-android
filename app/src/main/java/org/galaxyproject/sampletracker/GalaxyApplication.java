package org.galaxyproject.sampletracker;

import android.app.Application;

import roboguice.util.Ln;

/**
 * Main application object.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class GalaxyApplication extends Application {

    private static GalaxyApplication sInstance;

    public static GalaxyApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // Configure logging
        Ln.getConfig().setLoggingLevel(BuildConfig.LOG_LEVEL);
    }
}
