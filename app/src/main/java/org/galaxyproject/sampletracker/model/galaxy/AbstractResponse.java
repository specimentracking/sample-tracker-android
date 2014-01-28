package org.galaxyproject.sampletracker.model.galaxy;

/**
 * Abstract response to all request that cover error states.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public abstract class AbstractResponse {

    private String status;

    private String desc;

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
