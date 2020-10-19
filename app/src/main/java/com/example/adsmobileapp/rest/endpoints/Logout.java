package com.example.adsmobileapp.rest.endpoints;

import com.example.adsmobileapp.rest.RestApiConstants;
import com.example.adsmobileapp.rest.model.ResponseMessage;
import com.example.adsmobileapp.rest.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Logout {
    @Headers({RestApiConstants.HEADER_CONTENT_TYPE_AUTH})
    @POST(RestApiConstants.LOGOUT)
    Call<ResponseMessage> logout(@Body User user);
}
