package com.interview.healthcare.adpaters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.interview.healthcare.R;
import com.interview.healthcare.acitivites.WebViewActivity;
import com.interview.healthcare.databinding.ArticleItemRowBinding;
import com.interview.healthcare.models.ArticlesModel;
import com.interview.healthcare.utils.CommonUtils;
import com.interview.healthcare.utils.Connectivity;
import com.interview.healthcare.utils.ImageUtil;
import com.interview.healthcare.utils.PermissionUtils;
import com.interview.healthcare.utils.StringUtils;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.MyViewHolder> {

    Activity mActivity;
    ArrayList<ArticlesModel> articleArrayList;

    public ArticleListAdapter(Activity mActivity, ArrayList<ArticlesModel> articlesModel) {
        this.mActivity = mActivity;
        this.articleArrayList = articlesModel;

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

        ImageUtil.DisplayImage(mActivity,articleArrayList.get(position).getImageUrl(),holder.binding.imgArticle,R.drawable.ic_app_logo);
        StringUtils.setTextToTextView(holder.binding.tvHeader,articleArrayList.get(position).getTitle());
        StringUtils.setTextToTextView(holder.binding.tvAuthor,"- "+articleArrayList.get(position).getNewsSite());
        StringUtils.setTextToTextView(holder.binding.tvDesc,articleArrayList.get(position).getSummary());

        holder.binding.imgArticle.setOnClickListener(view -> ImageUtil.showImageDialog(mActivity,null,articleArrayList.get(position).getImageUrl(),2));
        holder.binding.btnReadmore.setOnClickListener(view -> {

           /* Intent intent = new Intent(mActivity, ArticleDetailScreen.class);
            intent.putExtra("position",position);
            intent.putExtra("articleList",articleArrayList.get(position));
            mActivity.startActivity(intent);*/

            // Open WebView for Detailed View
            Intent intent = new Intent(mActivity, WebViewActivity.class);
            intent.putExtra(mActivity.getString(R.string.URLIntent),articleArrayList.get(position).getUrl());
            intent.putExtra(mActivity.getString(R.string.WebViewHeader),articleArrayList.get(position).getNewsSite());
            mActivity.startActivity(intent);
        });

        holder.binding.imgShare.setOnClickListener(view -> {
            if (!StringUtils.isNull(articleArrayList.get(position).getImageUrl())) {
                // Share Details on WhatsApp with Image
                PermissionUtils.AsKPermissionToDownloadAndShareImage(mActivity, () -> {
                    if (Connectivity.isConnected(mActivity)){
                        final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                        String msgtoSend = "Hey check out this article by  \n" + articleArrayList.get(position).getNewsSite().toUpperCase() + " \n"+articleArrayList.get(position).getTitle()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                        CommonUtils.DownloadImageUsingGlideAndShare(mActivity,articleArrayList.get(position).getImageUrl(),msgtoSend);
                    }
                });

            } else {
                // Share Details on WhatsApp without image
                final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                String msgtoSend = "Hey check out this article by  \n" + articleArrayList.get(position).getNewsSite().toUpperCase() + " \n"+articleArrayList.get(position).getTitle()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;

                CommonUtils.shareonWhatsApp(mActivity,msgtoSend);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }


}
