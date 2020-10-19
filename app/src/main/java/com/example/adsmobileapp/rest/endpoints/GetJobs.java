package com.example.adsmobileapp.rest.endpoints;

import com.example.adsmobileapp.rest.RestApiConstants;
import com.example.adsmobileapp.rest.model.Job;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GetJobs {
    @Headers({RestApiConstants.HEADER_CONTENT_TYPE_AUTH})
    @GET(RestApiConstants.GET_JOBS)
    Call<List<Job>> getAllJobs();
}
