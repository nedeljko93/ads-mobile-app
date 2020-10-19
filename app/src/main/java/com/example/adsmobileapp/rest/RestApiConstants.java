package com.example.adsmobileapp.rest;

public class RestApiConstants {
    public static final long REST_CALL_TIMEOUT = 60000;
    public static final long REST_CONNECTION_TIMEOUT = 60000;
    public static final String HEADER_CONTENT_TYPE_AUTH = "Content-Type: application/json";


    public static final String BASE_URL = "http://172.20.20.6:8080";

    public static final String ADD_USER = "/api/v1/person";
    public static final String LOGIN = "/api/v1/person/login";
    public static final String LOGOUT = "/api/v1/person/logout";
    public static final String GET_JOBS = "api/v1/job/get";
    public static final String ADD_JOB = "api/v1/job/add";
}
