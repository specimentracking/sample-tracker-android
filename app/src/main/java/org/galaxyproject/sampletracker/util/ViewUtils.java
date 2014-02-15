package org.galaxyproject.sampletracker.util;

import android.view.View;

/**
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class ViewUtils {

    public static final void gone(View... views) {
        ensureVisibility(View.GONE, views);
    }

    public static final void invisible(View... views) {
        ensureVisibility(View.INVISIBLE, views);
    }

    public static final void visible(View... views) {
        ensureVisibility(View.VISIBLE, views);
    }

    public static final void conditionalGone(boolean shouldBeVisible, View... views) {
        if (shouldBeVisible) {
            ensureVisibility(View.VISIBLE, views);
        } else {
            ensureVisibility(View.GONE, views);
        }
    }

    public static final void conditionalInvisible(boolean shouldBeVisible, View... views) {
        if (shouldBeVisible) {
            ensureVisibility(View.VISIBLE, views);
        } else {
            ensureVisibility(View.INVISIBLE, views);
        }
    }

    private static void ensureVisibility(int visibility, View... views) {
        for (View view : views) {
            if (view != null && view.getVisibility() != visibility) {
                view.setVisibility(visibility);
            }
        }
    }
}
