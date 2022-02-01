package com.interview.healthcare.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.interview.healthcare.models.User;
import com.interview.healthcare.repository.AuthRepository;

public class SignUpViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<Boolean> isAlreadyExist = new MutableLiveData<>();

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public void registerUser(User user) throws Exception {
        authRepository.registerUser(user);
    }


    public LiveData<Boolean> isExist(String phone) throws Exception {

        isAlreadyExist.setValue(authRepository.CheckUser(phone));

        return isAlreadyExist;
    }

}
