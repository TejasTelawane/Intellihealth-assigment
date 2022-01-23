package com.interview.truemedinterview.repository;

import android.app.Activity;

import com.interview.truemedinterview.R;
import com.interview.truemedinterview.models.Model1;
import com.interview.truemedinterview.networkHelpers.RetrofitClient;
import com.interview.truemedinterview.networkHelpers.interfaces.GetAPIInterface;
import com.interview.truemedinterview.utils.CommonUtils;

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



    public void CallgetAPI( final GetVersionDetailsAPIListener APIResponseListener) {
        GetAPIInterface apiInterface = RetrofitClient.getInstance().getClient( "").create(GetAPIInterface.class);
        Call<Model1> responseCall = apiInterface.getData();
        responseCall.enqueue(new Callback<Model1>() {
            @Override
            public void onResponse(Call<Model1> call, Response<Model1> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Model1 versionAPIResponseModel = response.body();
                    if (APIResponseListener != null) {
                        APIResponseListener.onSuccess(versionAPIResponseModel);
                    }
                } else {
                    if (APIResponseListener != null) {
                        APIResponseListener.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model1> call, Throwable t) {
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


}
