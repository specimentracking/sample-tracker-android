package org.galaxyproject.sampletracker.net.galaxy.resource;

import org.galaxyproject.sampletracker.model.galaxy.specimen.Specimen;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @POST("/api/projects/{project_id}/specimens")
    @FormUrlEncoded
    public void create(@Query("key") String apiKey, @Path("project_id") String projectId,
            @Field("barcode") String barcode, @Field("parent_id") String parentId, @Field("state") String state,
            @Field("type") String type, @Field("location") String location, Callback<Specimen> callback);
}
