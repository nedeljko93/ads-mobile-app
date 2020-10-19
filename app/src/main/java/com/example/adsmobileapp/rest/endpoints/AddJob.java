package com.example.adsmobileapp.rest.endpoints;

import com.example.adsmobileapp.rest.RestApiConstants;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.rest.model.ResponseMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddJob {
    @Headers({RestApiConstants.HEADER_CONTENT_TYPE_AUTH})
    @POST(RestApiConstants.ADD_JOB)
    Call<ResponseMessage> addJob(@Body Job job);
}
