package org.galaxyproject.sampletracker.net.galaxy.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.galaxyproject.sampletracker.model.galaxy.specimen.SpecimenType;

import java.lang.reflect.Type;

/**
 * Custom GSON deserializer for {@link SpecimenType}.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenTypeDeserializer implements JsonDeserializer<SpecimenType> {

    @Override
    public SpecimenType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return SpecimenType.parse(json.getAsString());
    }
}
