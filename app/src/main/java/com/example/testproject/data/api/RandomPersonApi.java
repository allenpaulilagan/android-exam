package com.example.testproject.data.api;

import com.example.testproject.data.model.RandomPersonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomPersonApi {
    @GET("/api/?results=30&nat=us")
    Call<RandomPersonResponse> getPersons();


}
