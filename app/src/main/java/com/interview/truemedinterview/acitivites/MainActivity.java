package com.interview.truemedinterview.acitivites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.interview.truemedinterview.R;
import com.interview.truemedinterview.adpaters.ArticleListAdapter;
import com.interview.truemedinterview.databinding.ActivityMainBinding;
import com.interview.truemedinterview.models.ArticlesModel;
import com.interview.truemedinterview.utils.CommonUtils;
import com.interview.truemedinterview.utils.Constants;
import com.interview.truemedinterview.utils.StringUtils;
import com.interview.truemedinterview.viewmodels.ArticlesViewModel;

public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
   ActivityMainBinding binding;
    private CountDownTimer cdTimer = new CountDownTimer(Constants.ResndOTPTimeout, 1000) {
        public void onTick(long millisUntilFinished) {

            StringUtils.setTextToTextView(binding.tvCountdowntext, getString(R.string.articlesFetchCDmsg) + CommonUtils.covertTimeToText(millisUntilFinished));
            binding.btnFetchData.setEnabled(false);
            binding.tvError.setText(getString(R.string.strPleasewait));
            binding.tvError.setVisibility(View.VISIBLE);
            binding.recycleArticles.setVisibility(View.GONE);
        }

        public void onFinish() {
            binding.btnFetchData.setEnabled(true);
            StringUtils.setTextToTextView(binding.tvCountdowntext, getString(R.string.clicktoFetchArticlesaAgain));
            getData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initContext();
        initListeners();
        cdTimer.start();
    }

    private void initListeners() {
        binding.btnFetchData.setOnClickListener(view -> {
            cdTimer.start();
        });
    }

    private void initContext() {
        mActivity = MainActivity.this;
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

    private void DisplayDatain_UI(ArticlesModel articleModel) {
        if (!StringUtils.isNull(articleModel) && !StringUtils.isNull(articleModel.getResult()) && !StringUtils.isNull(articleModel.getResult().getArticle())){
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
    protected void onStop() {
        super.onStop();

        // To stop countdown when acitivty is closed.
        if (cdTimer != null){
            cdTimer.cancel();
        }
        binding.btnFetchData.setEnabled(true);
        StringUtils.setTextToTextView(binding.tvCountdowntext, getString(R.string.clicktoFetchArticlesaAgain));
    }
}