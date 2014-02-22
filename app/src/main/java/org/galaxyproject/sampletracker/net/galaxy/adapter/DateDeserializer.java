package org.galaxyproject.sampletracker.net.galaxy.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.galaxyproject.sampletracker.util.DateTimeUtils;

import roboguice.util.Ln;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Custom GSON deserializer for {@link Date} that is timezone aware.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.TIMESTAMP_PATTERN, Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return formatter.parse(json.getAsString());
        } catch (ParseException e) {
            Ln.w(e, "Failed to parse server Date");
            return null;
        }
    }
}
