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

        CardView cv_menuItem;
        TextView tv_header,tv_author,tv_desc;
        ShapeableImageView img_article,img_backarrow,img_share;
        MaterialButton btn_readmore;

        public MyViewHolder(View view, int ViewType) {
            super(view);
            cv_menuItem = (CardView) view.findViewById(R.id.cv_menuItem);
            img_article = (ShapeableImageView) view.findViewById(R.id.img_article);
            img_backarrow = (ShapeableImageView) view.findViewById(R.id.img_backarrow);
            img_share = (ShapeableImageView) view.findViewById(R.id.img_share);
            tv_header = (TextView) view.findViewById(R.id.tv_header);
            tv_author = (TextView) view.findViewById(R.id.tv_author);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            btn_readmore = (MaterialButton) view.findViewById(R.id.btn_readmore);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_row, parent, false);
        MyViewHolder vhUser = new MyViewHolder(view, viewType);

        return vhUser;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageUtil.DisplayImage(mActivity,articleArrayList.get(position).getUrl(),holder.img_article,R.drawable.truemeds_image);
        StringUtils.setTextToTextView(holder.tv_header,articleArrayList.get(position).getName());
        StringUtils.setTextToTextView(holder.tv_author,"- "+articleArrayList.get(position).getAuthor());
        StringUtils.setTextToTextView(holder.tv_desc,articleArrayList.get(position).getDescription());
        holder.btn_readmore.setOnClickListener(view -> {

            Intent intent = new Intent(mActivity, ArticleDetailScreen.class);
            intent.putExtra("position",position);
            intent.putExtra("articleList",articlesModel);
            mActivity.startActivity(intent);
        });

        holder.img_share.setOnClickListener(view -> {
            if (!StringUtils.isNull(articleArrayList.get(position).getImage())) {

                PermissionUtils.AsKPermissionToDownloadAndShareImage(mActivity, () -> {
                    if (Connectivity.isConnected(mActivity)){
                        final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                        String msgtoSend = "Hey check out this article by  \n" + articleArrayList.get(position).getAuthor().toUpperCase() + " \n"+articleArrayList.get(position).getName()+"\nDownload the App now" + "https://play.google.com/store/apps/details?id=" + appPackageName;
                        CommonUtils.DownloadImageUsingGlide(mActivity,articleArrayList.get(position).getUrl(),msgtoSend);
                    }
                });

            } else {

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
