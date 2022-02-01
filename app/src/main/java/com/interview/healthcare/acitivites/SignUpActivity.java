package com.interview.healthcare.acitivites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.interview.healthcare.R;
import com.interview.healthcare.customClass.CustomDialogs;
import com.interview.healthcare.databinding.ActivitySignUpBinding;
import com.interview.healthcare.models.User;
import com.interview.healthcare.utils.CommonUtils;
import com.interview.healthcare.utils.Constants;
import com.interview.healthcare.utils.KeyBoard;
import com.interview.healthcare.utils.StringUtils;
import com.interview.healthcare.utils.Validator;
import com.interview.healthcare.viewmodels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mActivity = SignUpActivity.this;
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        init();

    }

    private void init() {

        binding.btnSignin.setOnClickListener(view -> {

            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        binding.btnSignup.setOnClickListener(view ->
                {
                    try {
                        registerUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    // validate and Register User.
    public void registerUser() throws Exception {
        if (Validate()) {
            signUpViewModel.isExist(binding.edtMobile.getText().toString().trim()).observe(this, aBoolean -> {
                if (aBoolean) {
                    CustomDialogs.showCustomAlertWithPositiveButton(mActivity, getString(R.string.alert), getString(R.string.userAlreadyRegistered), R.drawable.ic_info, "OK", new CustomDialogs.OnAlertClickListener() {
                        @Override
                        public void onPositiveButtonClicked() {

                        }
                        @Override
                        public void onNegativeButtonClicked() {
                        }
                    });
                } else {

                    // Show Dialog to enter OTP.
                    String strOTPAlrtmsg = getString(R.string.enterOtpsendToMob) + StringUtils.replaceMobileNumberwithstar(binding.edtMobile.getText().toString().trim(), 2);
                    CustomDialogs.showCustomOTPValidateDialogBox(mActivity, strOTPAlrtmsg, otp -> {

                        if (StringUtils.CheckEqualIgnoreCase(otp,"1234")){
                            User user = new User();
                            user.setFirst_name(binding.edtFirstname.getText().toString().trim());
                            user.setLast_name(binding.edtLastname.getText().toString().trim());
                            user.setPhoneNo(binding.edtMobile.getText().toString().trim());
                            user.setEmail(!StringUtils.isNull(binding.edtEmail.getText().toString().trim()) ? binding.edtEmail.getText().toString().trim() : "");
                            user.setPassword(binding.edtPassword.getText().toString().trim());

                            try {
                                signUpViewModel.registerUser(user);
                                CustomDialogs.showCustomAlertWithPositiveButton(mActivity, getString(R.string.userRegisteredSuccessfully), getString(R.string.loginViaMobandPass), R.drawable.ic_success, getString(R.string.strOK), new CustomDialogs.OnAlertClickListener() {
                                    @Override
                                    public void onPositiveButtonClicked() {
                                        gotoSignIn();
                                    }
                                    @Override
                                    public void onNegativeButtonClicked() {
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            CommonUtils.showToast(mActivity,getString(R.string.invalidOTP));
                        }

                    });
                }
            });
        }
    }

    // Validate Details Entered by User
    private boolean Validate() {
        boolean isvalid = true;
        String alertmsg = "";
        if (StringUtils.isNull(binding.edtFirstname.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterFirstName;
        }else if(!Validator.isValidFirstName(binding.edtFirstname.getText().toString().trim())){
            isvalid = false;
            alertmsg = Constants.PleaseEnterValidFirstName;
        }else if (StringUtils.isNull(binding.edtLastname.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterLastName;
        }else if(!Validator.isValidLastName(binding.edtLastname.getText().toString().trim())){
            isvalid = false;
            alertmsg = Constants.PleaseEnterValidLastName;
        }else if (StringUtils.isNull(binding.edtMobile.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterMobileno;
        }else if(!Validator.isValidMobile(binding.edtMobile.getText().toString().trim())){
            isvalid = false;
            alertmsg = Constants.PleaseEnterValidMobileno;
        }else if (!StringUtils.isNull(binding.edtEmail.getText().toString().trim()) && !Validator.isValidEmail(binding.edtEmail.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterValidEmail;
        }else if (StringUtils.isNull(binding.edtPassword.getText().toString().trim())) {
            isvalid = false;
            alertmsg = Constants.PleaseEnterPassword;
        }else if(!Validator.isValidPassword(binding.edtPassword.getText().toString().trim())){
            isvalid = false;
            alertmsg = Constants.PleaseEnterValidPassword;
        }else if(!StringUtils.CheckEqualCaseSensitive(binding.edtPassword.getText().toString().trim(),binding.edtConfirmPassword.getText().toString().trim())){
            isvalid = false;
            alertmsg = Constants.PasswordshouldMatch;
        }

        if (!isvalid){
            KeyBoard.hideKeyboard(mActivity);
            CommonUtils.showToast(mActivity,alertmsg);
        }
        return isvalid;
    }

    public void gotoSignIn() {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}