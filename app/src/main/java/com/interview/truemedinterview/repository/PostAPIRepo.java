package com.interview.truemedinterview.repository;

import com.interview.truemedinterview.models.ArticlesModel;
import com.interview.truemedinterview.networkHelpers.RetrofitClient;
import com.interview.truemedinterview.networkHelpers.interfaces.PostAPIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Repository for all Post APIs
public class PostAPIRepo {

    public static PostAPIRepo postAPI_singletonClass;
    String strBaseUrl = "https://stage-services.truemeds.in/";

    public PostAPIRepo() {

    }

    public static PostAPIRepo getInstance() {
        if (postAPI_singletonClass == null) {
            postAPI_singletonClass = new PostAPIRepo();
        }
        return postAPI_singletonClass;
    }

    // get Article network call via retrofit
    public void CallArticlesAPI(final OnArticlesAPIResponseListener APIResponseListener) {

        PostAPIInterface apiInterface = RetrofitClient.getInstance().getClient(strBaseUrl).create(PostAPIInterface.class);
        Call<ArticlesModel> responseCall = apiInterface.getArticleAPI();
        responseCall.enqueue(new Callback<ArticlesModel>() {
            @Override
            public void onResponse(Call<ArticlesModel> call, Response<ArticlesModel> response) {
                if (response.body() != null) {
                    ArticlesModel ResponseModel = response.body();

                    if (APIResponseListener != null) {
                        APIResponseListener.onSuccess(ResponseModel);
                    }
                } else {
                    if (APIResponseListener != null) {
                        APIResponseListener.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticlesModel> call, Throwable t) {
                if (APIResponseListener != null) {
                    APIResponseListener.onFailure();
                }
            }
        });

    }


    //-----------------------------------------------------------Listeners ---------------------------------------------------------
    public interface OnArticlesAPIResponseListener {
        void onSuccess(ArticlesModel model);

        void onFailure();
    }


}
