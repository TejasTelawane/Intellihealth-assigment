package com.interview.healthcare.networkHelpers.interfaces;

import com.interview.healthcare.models.ArticlesModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAPIInterface {


    @GET("articles")
    Call<ArrayList<ArticlesModel>> getArticleAPI();

}
