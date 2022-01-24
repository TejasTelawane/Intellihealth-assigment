package com.interview.truemedinterview.adpaters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.interview.truemedinterview.R;
import com.interview.truemedinterview.acitivites.ArticleDetailScreen;
import com.interview.truemedinterview.databinding.ArticleItemRowBinding;
import com.interview.truemedinterview.models.ArticlesModel;
import com.interview.truemedinterview.utils.CommonUtils;
import com.interview.truemedinterview.utils.Connectivity;
import com.interview.truemedinterview.utils.ImageUtil;
import com.interview.truemedinterview.utils.PermissionUtils;
import com.interview.truemedinterview.utils.StringUtils;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.MyViewHolder> {

    Activity mActivity;
    ArrayList<ArticlesModel.Article> articleArrayList;
    ArticlesModel articlesModel;

    public ArticleListAdapter(Activity mActivity, ArticlesModel articlesModel) {
        this.mActivity = mActivity;
        this.articlesModel = articlesModel;
        this.articleArrayList = articlesModel.getResult().getArticle();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ArticleItemRowBinding binding;//Name of the test_list_item.xml in camel case + "Binding"
        public MyViewHolder(ArticleItemRowBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ArticleItemRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageUtil.DisplayImage(mActivity,articleArrayList.get(position).getImage(),holder.binding.imgArticle,R.drawable.truemeds_image);
        StringUtils.setTextToTextView(holder.binding.tvHeader,articleArrayList.get(position).getName());
        StringUtils.setTextToTextView(holder.binding.tvAuthor,"- "+articleArrayList.get(position).getAuthor());
        StringUtils.setTextToTextView(holder.binding.tvDesc,articleArrayList.get(position).getDescription());
        holder.binding.btnReadmore.setOnClickListener(view -> {

            Intent intent = new Intent(mActivity, ArticleDetailScreen.class);
            intent.putExtra("position",position);
            intent.putExtra("articleList",articlesModel);
            mActivity.startActivity(intent);
        });

        holder.binding.imgShare.setOnClickListener(view -> {
            if (!StringUtils.isNull(articleArrayList.get(position).getImage())) {
                // Share Details on WhatsApp with Image
                PermissionUtils.AsKPermissionToDownloadAndShareImage(mActivity, () -> {
                    if (Connectivity.isConnected(mActivity)){
                        final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                        String msgtoSend = "Hey check out this article by  \n" + articleArrayList.get(position).getAuthor().toUpperCase() + " \n"+articleArrayList.get(position).getName()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                        CommonUtils.DownloadImageUsingGlide(mActivity,articleArrayList.get(position).getImage(),msgtoSend);
                    }
                });

            } else {
                // Share Details on WhatsApp without image
                final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                String msgtoSend = "Hey check out this article by  \n" + articleArrayList.get(position).getAuthor().toUpperCase() + " \n"+articleArrayList.get(position).getName()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;

                CommonUtils.shareonWhatsApp(mActivity,msgtoSend);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }


}
