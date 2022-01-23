package com.interview.truemedinterview.networkHelpers.interfaces;

import com.interview.truemedinterview.models.Model1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAPIInterface {

    @GET("version")
    Call<Model1> getData();

}
