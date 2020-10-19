package com.example.adsmobileapp.rest.endpoints;

import com.example.adsmobileapp.rest.RestApiConstants;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.rest.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetAllUsers {
    @Headers({RestApiConstants.HEADER_CONTENT_TYPE_AUTH})
    @GET(RestApiConstants.GET_JOBS)
    Call<List<Job>> getAllUsers();
}
