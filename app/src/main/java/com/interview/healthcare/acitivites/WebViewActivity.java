package com.interview.healthcare.acitivites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.interview.healthcare.R;
import com.interview.healthcare.customClass.CustomDialogs;
import com.interview.healthcare.databinding.ActivityWebViewBinding;
import com.interview.healthcare.utils.CommonUtils;
import com.interview.healthcare.utils.StringUtils;

public class WebViewActivity extends AppCompatActivity {

    Activity mActivity;
    String URLToOpen;
    String Heading;
    ActivityWebViewBinding binding;
    private boolean gobackHomeScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initContext();
        initListener();
        initData();
    }

    private void initContext() {
        mActivity = WebViewActivity.this;

    }

    private void initListener() {

        // To Exit Webview
        binding.imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogs.showCustomAlertWithPositiveAndNegativeButton(mActivity,  getString(R.string.Exit),"", R.drawable.ic_info, getString(R.string.strYes), getString(R.string.strNo), new CustomDialogs.OnAlertClickListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        processBackPressed();
                    }

                    @Override
                    public void onNegativeButtonClicked() {

                    }
                });

            }
        });
    }

    private void initData() {
        // Extract url from intent
        if (getIntent().hasExtra(getString(R.string.URLIntent))&& getIntent().hasExtra(getString(R.string.WebViewHeader))){
            URLToOpen = getIntent().getStringExtra(getString(R.string.URLIntent));
            Heading = getIntent().getStringExtra(getString(R.string.WebViewHeader));
            binding.tvPageHeading.setText(Heading);
            if (!StringUtils.isNull(URLToOpen) && Patterns.WEB_URL.matcher(URLToOpen.toLowerCase()).matches()){
                OpenURL();
            }else{
                CommonUtils.showToast(mActivity,getString(R.string.UrlNotFound));
                finish();
            }
        }else{
            CommonUtils.showToast(mActivity,getString(R.string.UrlNotFound));
            finish();
        }
    }

    // OPen URL in WebView
    private void OpenURL() {
        binding.WebView.getSettings().setJavaScriptEnabled(true);
        binding.WebView.loadUrl(URLToOpen);
        binding.WebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                //True if the host application wants to leave the current WebView and handle the url itself, otherwise return false.
                if (url.contains(URLToOpen)) {
                    processBackPressed();
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (failingUrl.contains(URLToOpen)) {
                    processBackPressed();
                } else {
                    CommonUtils.showToast(mActivity,"Server unreachable");
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Show progress Dialog is Page is not finished loading..
                CommonUtils.showProgressDialog(mActivity, "", mActivity.getString(R.string.PleaseWait), true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                // Dismiss progress Dialog after Page is loaded Completely..
                new pdHandler().sendEmptyMessage(0);
            }

        });
    }

    class pdHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            CommonUtils.hideProgressDialog(mActivity);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.WebView.canGoBack()){
            binding.WebView.goBack();
        } else {
            processBackPressed();
        }
    }

    public void processBackPressed() {
        super.onBackPressed();
    }

}