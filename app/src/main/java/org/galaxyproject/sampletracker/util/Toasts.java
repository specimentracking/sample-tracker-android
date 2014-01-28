package org.galaxyproject.sampletracker.util;

import android.content.Context;
import android.widget.Toast;

import org.galaxyproject.sampletracker.GalaxyApplication;

/**
 * Convenience class for displaying the Toasts in {@link Context} of application and to not forget to call show()
 * method.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class Toasts {

    private Toasts() {
    }

    /*
     * General toasts
     */
    public static void showShort(CharSequence text) {
        Toast.makeText(GalaxyApplication.get(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(GalaxyApplication.get(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(CharSequence text) {
        Toast.makeText(GalaxyApplication.get(), text, Toast.LENGTH_LONG).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(GalaxyApplication.get(), resId, Toast.LENGTH_LONG).show();
    }
}
