package com.interview.healthcare.acitivites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.interview.healthcare.R;
import com.interview.healthcare.adpaters.ArticleListAdapter;
import com.interview.healthcare.customClass.CustomDialogs;
import com.interview.healthcare.databinding.ActivityMainBinding;
import com.interview.healthcare.helper.AppPreferenceManager;
import com.interview.healthcare.models.ArticlesModel;
import com.interview.healthcare.utils.CommonUtils;
import com.interview.healthcare.utils.Connectivity;
import com.interview.healthcare.utils.StringUtils;
import com.interview.healthcare.viewmodels.ArticlesViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
   ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initContext();
        initListeners();
        initData();
    }

    private void initContext() {
        mActivity = MainActivity.this;
        setSupportActionBar(binding.actionBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
            getSupportActionBar().show();
        }
        AppPreferenceManager.init(mActivity);

    }


    private void initListeners() {

    }

    private void initData() {

        if (Connectivity.isConnected(mActivity)){
            getData();
        }

    }


    // Get Live Data for Articles from ViewModel
    private void getData() {
        ArticlesViewModel articlesViewModel = new ArticlesViewModel();
        articlesViewModel.getArticles().observe(this, articleModel -> {
            DisplayDatain_UI(articleModel);
        });

        articlesViewModel.showProgress().observe(this, aBoolean -> {
            if (aBoolean){
                binding.recycleArticles.setVisibility(View.GONE);
                CommonUtils.showProgressDialog(mActivity,"","",false);
            }else{
                binding.recycleArticles.setVisibility(View.VISIBLE);
                CommonUtils.hideProgressDialog(mActivity);
            }
        });
    }

    private void DisplayDatain_UI(ArrayList<ArticlesModel> articleModel) {
        if (!StringUtils.isNull(articleModel)){
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            binding.recycleArticles.setLayoutManager(layoutManager);
            binding.recycleArticles.setHasFixedSize(true);
            binding.recycleArticles.setVisibility(View.VISIBLE);
            binding.tvError.setVisibility(View.GONE);
            ArticleListAdapter articleListAdapter = new ArticleListAdapter(mActivity,articleModel);
            binding.recycleArticles.setAdapter(articleListAdapter);
        }else{
            binding.tvError.setText(getString(R.string.strNoDatafound));
            binding.tvError.setVisibility(View.VISIBLE);
            binding.recycleArticles.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spanString.length(), 0);
            int end = spanString.length();
            spanString.setSpan(new RelativeSizeSpan(.8f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);

        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {
            CustomDialogs.showCustomAlertWithPositiveAndNegativeButton(mActivity, getString(R.string.logout), getString(R.string.areyousure), R.drawable.ic_info, getString(R.string.strYes), getString(R.string.strNo), new CustomDialogs.OnAlertClickListener() {
                @Override
                public void onPositiveButtonClicked() {
                    AppPreferenceManager.clearAllPreferences();
                    startActivity(new Intent(mActivity, SignInActivity.class));
                    finish();
                }

                @Override
                public void onNegativeButtonClicked() {

                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}