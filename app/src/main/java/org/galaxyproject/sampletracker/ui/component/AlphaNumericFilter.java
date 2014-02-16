package org.galaxyproject.sampletracker.ui.component;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * {@link InputFilter} that allow only letters or numbers.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class AlphaNumericFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (!Character.isLetterOrDigit(source.charAt(i))) {
                return "";
            }
        }
        return null;
    }
}
