package com.interview.healthcare.repository;

import com.interview.healthcare.models.ArticlesModel;
import com.interview.healthcare.models.Model1;
import com.interview.healthcare.networkHelpers.RetrofitClient;
import com.interview.healthcare.networkHelpers.interfaces.GetAPIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Repository for all Get APIs
public class GetAPIRepo {

    public static GetAPIRepo getAPI_singletonClass;

    public GetAPIRepo() {

    }

    public static GetAPIRepo getInstance() {
        if (getAPI_singletonClass == null) {
            getAPI_singletonClass = new GetAPIRepo();
        }
        return getAPI_singletonClass;
    }



    public void CallArticlesAPI(final OnArticlesAPIResponseListener APIResponseListener) {

        GetAPIInterface apiInterface = RetrofitClient.getInstance().getClient("https://api.spaceflightnewsapi.net/v3/").create(GetAPIInterface.class);
        Call<ArrayList<ArticlesModel>> responseCall = apiInterface.getArticleAPI();
        responseCall.enqueue(new Callback<ArrayList<ArticlesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ArticlesModel>> call, Response<ArrayList<ArticlesModel>> response) {
                if (response.body() != null) {
                    ArrayList<ArticlesModel> ResponseModel = response.body();

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
            public void onFailure(Call<ArrayList<ArticlesModel>> call, Throwable t) {
                if (APIResponseListener != null) {
                    APIResponseListener.onFailure();
                }
            }
        });

    }



    // TODO INTERFACE --------------------------------------------------------------------------------------------------------------------------------------------------------

    public interface GetVersionDetailsAPIListener {
        void onSuccess(Model1 versionAPIResponseModel);

        void onFailure();
    }

    public interface OnArticlesAPIResponseListener {
        void onSuccess(ArrayList<ArticlesModel> model);

        void onFailure();
    }

}
