package com.interview.truemedinterview.acitivites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.interview.truemedinterview.R;
import com.interview.truemedinterview.databinding.ActivityArticleDetailScreenBinding;
import com.interview.truemedinterview.models.ArticlesModel;
import com.interview.truemedinterview.utils.CommonUtils;
import com.interview.truemedinterview.utils.Connectivity;
import com.interview.truemedinterview.utils.Constants;
import com.interview.truemedinterview.utils.ImageUtil;
import com.interview.truemedinterview.utils.PermissionUtils;
import com.interview.truemedinterview.utils.StringUtils;

import java.io.ByteArrayOutputStream;

public class ArticleDetailScreen extends AppCompatActivity {

    private Activity mActivity;
    ActivityArticleDetailScreenBinding binding;
    private int positiontodisplay = 0;
    private ArticlesModel articleMainModel;
    private ArticlesModel.Article singleArticleModel;

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
        articleMainModel = (ArticlesModel) getIntent().getSerializableExtra("articleList");
        singleArticleModel = articleMainModel.getResult().getArticle().get(positiontodisplay);
    }

    private void initData() {

        ImageUtil.DisplayImage(mActivity,singleArticleModel.getUrl(),binding.imgArticle,R.drawable.truemeds_image);
        StringUtils.setTextToTextView(binding.tvDate,singleArticleModel.getCreatedOn());
        StringUtils.setTextToTextView(binding.tvCategory,singleArticleModel.getCategoryName());
        StringUtils.setTextToTextView(binding.tvHeader,singleArticleModel.getName());
        StringUtils.setTextToTextView(binding.tvAuthor,"- "+singleArticleModel.getAuthor());
        StringUtils.setTextToTextView(binding.tvDesc,singleArticleModel.getDescription());
        binding.imgBackarrow.setOnClickListener(view -> {
            finish();
        });

        binding.imgShare.setOnClickListener(view -> {

            if (!StringUtils.isNull(singleArticleModel.getImage())) {
                // Share Details on WhatsApp with Image
                PermissionUtils.AsKPermissionToDownloadAndShareImage(mActivity, () -> {
                    if (Connectivity.isConnected(mActivity)){
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        String msgtoSend = "Hey check out this article by  \n" + singleArticleModel.getAuthor().toUpperCase() + " \n"+singleArticleModel.getName()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                        CommonUtils.DownloadImageUsingGlide(mActivity,singleArticleModel.getUrl(),msgtoSend);
                    }
                });

            } else {
                // Share Details on WhatsApp without image
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                String msgtoSend = "Hey check out this article by  \n" + singleArticleModel.getAuthor().toUpperCase() + " \n"+singleArticleModel.getName()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                CommonUtils.shareonWhatsApp(mActivity,msgtoSend);
            }

        });
    }




}