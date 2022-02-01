package com.interview.healthcare.acitivites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.interview.healthcare.R;
import com.interview.healthcare.databinding.ActivitySignInBinding;
import com.interview.healthcare.utils.CommonUtils;
import com.interview.healthcare.utils.Constants;
import com.interview.healthcare.utils.KeyBoard;
import com.interview.healthcare.utils.StringUtils;
import com.interview.healthcare.utils.Validator;
import com.interview.healthcare.viewmodels.SignInViewModel;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private SignInViewModel signInViewModel;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        mActivity = SignInActivity.this;
        init();

    }

    private void init() {

        binding.signUpButton.setOnClickListener(view -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));


        binding.signInButton.setOnClickListener(view ->
        {
            if (Validate()) { // Validate details

                // Check and Sign in User
                signInViewModel.signInUser(binding.edtMobile.getText().toString().trim(), binding.edtPassword.getText().toString().trim());

                // Check Sign in
                signInViewModel.checkSignIn().observe(this, aBoolean -> {
                    if (aBoolean) {
                        CommonUtils.showToast(mActivity,getString(R.string.LoginSuccessfull));
                        goToHomeScreen();
                    } else {
                        CommonUtils.showToast(mActivity,getString(R.string.invalidUserAndPassword));
                    }
                });
            }

        });

    }

    private boolean Validate() {
        boolean isvalid = true;
        String alertmsg = "";
        if (StringUtils.isNull(binding.edtMobile.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterMobileno;
        }else if(!Validator.isValidMobile(binding.edtMobile.getText().toString().trim())){
            isvalid = false;
            alertmsg = Constants.PleaseEnterValidMobileno;
        }else if (StringUtils.isNull(binding.edtPassword.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterPassword;
        }

        if (!isvalid){
            KeyBoard.hideKeyboard(mActivity);
            CommonUtils.showToast(mActivity,alertmsg);
        }
        return isvalid;
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}