package org.galaxyproject.sampletracker.model.galaxy;

import com.google.gson.annotations.SerializedName;

/**
 * Abstract response to all request that cover error states.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public abstract class AbstractResponse {

    @SerializedName("err_code") private String errorCode;
    @SerializedName("err_msg") private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
