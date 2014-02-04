package org.galaxyproject.sampletracker.model.galaxy.specimen;

import org.galaxyproject.sampletracker.model.galaxy.AbstractResponse;

/**
 * Model of response that contains single specimen.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class SpecimenResponse extends AbstractResponse {

    private Specimen specimen;

    public Specimen getSpecimen() {
        return specimen;
    }
}
