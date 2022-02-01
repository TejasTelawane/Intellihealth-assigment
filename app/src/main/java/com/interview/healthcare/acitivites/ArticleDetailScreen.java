package com.interview.healthcare.acitivites;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.interview.healthcare.R;
import com.interview.healthcare.databinding.ActivityArticleDetailScreenBinding;
import com.interview.healthcare.models.ArticlesModel;
import com.interview.healthcare.utils.CommonUtils;
import com.interview.healthcare.utils.Connectivity;
import com.interview.healthcare.utils.ImageUtil;
import com.interview.healthcare.utils.PermissionUtils;
import com.interview.healthcare.utils.StringUtils;

public class ArticleDetailScreen extends AppCompatActivity {

    private Activity mActivity;
    ActivityArticleDetailScreenBinding binding;
    private int positiontodisplay = 0;
    private ArticlesModel singleArticleModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleDetailScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initContext();
        initData();
    }

    private void initContext() {
        mActivity = ArticleDetailScreen.this;
        positiontodisplay = getIntent().getIntExtra("position",0);
        singleArticleModel = (ArticlesModel) getIntent().getSerializableExtra("articleList");
    }

    private void initData() {

        ImageUtil.DisplayImage(mActivity,singleArticleModel.getImageUrl(),binding.imgArticle,R.drawable.ic_app_logo);
        StringUtils.setTextToTextView(binding.tvDate,singleArticleModel.getPublishedAt());
        StringUtils.setTextToTextView(binding.tvCategory,"");
        StringUtils.setTextToTextView(binding.tvHeader,singleArticleModel.getTitle());
        StringUtils.setTextToTextView(binding.tvAuthor,"- "+singleArticleModel.getNewsSite());
        StringUtils.setTextToTextView(binding.tvDesc,singleArticleModel.getSummary());
        binding.imgBackarrow.setOnClickListener(view -> {
            finish();
        });

        binding.imgShare.setOnClickListener(view -> {

            if (!StringUtils.isNull(singleArticleModel.getImageUrl())) {
                // Share Details on WhatsApp with Image
                PermissionUtils.AsKPermissionToDownloadAndShareImage(mActivity, () -> {
                    if (Connectivity.isConnected(mActivity)){
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        String msgtoSend = "Hey check out this article by  \n" + singleArticleModel.getNewsSite().toUpperCase() + " \n"+singleArticleModel.getTitle()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                        CommonUtils.DownloadImageUsingGlideAndShare(mActivity,singleArticleModel.getImageUrl(),msgtoSend);
                    }
                });

            } else {
                // Share Details on WhatsApp without image
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                String msgtoSend = "Hey check out this article by  \n" + singleArticleModel.getNewsSite().toUpperCase() + " \n"+singleArticleModel.getTitle()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                CommonUtils.shareonWhatsApp(mActivity,msgtoSend);
            }

        });
    }




}