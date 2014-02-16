package org.galaxyproject.sampletracker.net.galaxy.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenLocation;

import java.lang.reflect.Type;

/**
 * Custom GSON deserializer for {@link SpecimenLocation}.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenLocationDeserializer implements JsonDeserializer<SpecimenLocation> {

    @Override
    public SpecimenLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return SpecimenLocation.parse(json.getAsString());
    }
}
