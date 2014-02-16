package org.galaxyproject.sampletracker.net.galaxy.resource;

import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * {@link RestAdapter} resource for handling of specimen data.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public interface SpecimenResource {

    @GET("/api/projects/{project_id}/check")
    public void check(@Query("key") String apiKey, @Path("project_id") String projectId,
            @Query("barcode") String barcode, Callback<Specimen> callback);
}
