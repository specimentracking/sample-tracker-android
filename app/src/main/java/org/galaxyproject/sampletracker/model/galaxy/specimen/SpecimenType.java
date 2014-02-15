package org.galaxyproject.sampletracker.model.galaxy.specimen;

import android.text.TextUtils;

import com.google.common.base.Splitter;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Wraps a logic of specimen type format.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenType {

    /**
     * Returns array of 3 items - type of material, acid and sub acid.
     * 
     * @param type Type value to parse
     */
    @Nonnull
    public static String[] parse(String type) {
        String[] result = new String[3];
        if (TextUtils.isEmpty(type)) {
            return result;
        }

        List<String> values = Splitter.on('-').splitToList(type);
        int size = values.size();
        if (size >= 1) {
            result[0] = values.get(0);
        }
        if (size >= 2) {
            result[1] = values.get(1);
        }
        if (size >= 3) {
            result[2] = values.get(2);
        }

        return result;
    }
}
