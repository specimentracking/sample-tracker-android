package org.galaxyproject.sampletracker.ui.component;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * {@link EmptyTextWatcher} instance that does not nothing but allows children implementations to overwrite.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public class EmptyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Nothing, override when needed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Nothing, override when needed
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Nothing, override when needed
    }
}
