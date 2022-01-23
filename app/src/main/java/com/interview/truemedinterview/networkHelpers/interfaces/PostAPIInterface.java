package com.interview.truemedinterview.networkHelpers.interfaces;


import com.interview.truemedinterview.models.ArticlesModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostAPIInterface {

    @Headers("Content-Type: application/json")
    @POST("ArticleService/getArticleListing")
    Call<ArticlesModel> getArticleAPI();


}
