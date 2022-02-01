package com.interview.healthcare.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.interview.healthcare.helper.AppPreferenceManager;
import com.interview.healthcare.models.User;
import com.interview.healthcare.repository.AuthRepository;


public class SignInViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private MutableLiveData<Boolean> checkSignIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAlreadyExist = new MutableLiveData<>();

    public SignInViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        AppPreferenceManager.init(getApplication().getApplicationContext());
    }

    public void signInUser(String mobile, String pass) {

        try {
            User user = authRepository.loginUser(mobile, pass);
            if (user != null) {
                AppPreferenceManager.setLoginModel(user);
                checkSignIn.setValue(true);
            } else
                checkSignIn.setValue(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<Boolean> isExist(String phone) throws Exception {

        isAlreadyExist.setValue(authRepository.CheckUser(phone));

        return isAlreadyExist;
    }


    public LiveData<Boolean> checkSignIn() {
        return checkSignIn;
    }


    public LiveData<Boolean> isLoggedIn() {

        User user = AppPreferenceManager.getLoginModel();

        if (user != null)
            isLoggedIn.setValue(true);
        else
            isLoggedIn.setValue(false);


        return isLoggedIn;
    }
}
