package com.interview.healthcare.networkHelpers.interfaces;


import com.interview.healthcare.models.ArticlesModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostAPIInterface {

    @Headers("Content-Type: application/json")
    @POST("ArticleService/getArticleListing")
    Call<ArrayList<ArticlesModel>> getArticleAPI();


}
