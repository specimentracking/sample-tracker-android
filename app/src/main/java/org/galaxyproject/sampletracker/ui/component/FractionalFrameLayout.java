package org.galaxyproject.sampletracker.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Pavel Sveda <pavel.sveda@cleverlance.com>
 */
public final class FractionalFrameLayout extends FrameLayout {

    public FractionalFrameLayout(Context context) {
        super(context);
    }

    public FractionalFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FractionalFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public float getXFraction() {
        int width = getWidth();
        return width > 0 ? getX() / width : 1f;
    }

    public void setXFraction(float xFraction) {
        int width = getWidth();
        setX((width > 0) ? (xFraction * width) : -9999);
    }
}
