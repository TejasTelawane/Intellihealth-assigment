package com.interview.truemedinterview.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.interview.truemedinterview.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {



    public static void DisplayImage(Activity activity, String Url, ImageView imageView, int defaultimage) {
        try {
            if (!StringUtils.isNull(Url)) {
                Glide.with(activity).load(AddUserAgentToGlide(activity, Url))
                        .placeholder(defaultimage).dontAnimate()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
                        .error(defaultimage)
                        .into(imageView);
            } else {
                DisplayDefaultImage(activity, imageView, defaultimage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static GlideUrl AddUserAgentToGlide(Activity activity, String Url) {
        return new GlideUrl(Url, new LazyHeaders.Builder()
                .addHeader(Constants.HEADER_USER_AGENT, "")
                .build());
    }

    private static void DisplayDefaultImage(Activity activity, ImageView imageView, int defaultimage) {
        Glide.with(activity).load("")
                .placeholder(defaultimage).dontAnimate()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
                .error(defaultimage)
                .into(imageView);
    }

    public void DisplayDeviceImage(Activity activity, String Url, ImageView imageView, int defaultimage) {
        try {
            Glide.with(activity).load(Url)
                    .placeholder(defaultimage).dontAnimate()
                    //              .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //              .skipMemoryCache(true)
                    .error(defaultimage)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File GetImageFileFromBitmap(Activity activity, Bitmap bitmap, String filename) {
        File imagefile = null;

        try {
            imagefile = new File(activity.getCacheDir(), filename);
            imagefile.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(imagefile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagefile;
    }

    public static void showImageDialog(Activity activity, File imageFile, String url, int flag) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog);
        dialog.setCancelable(true);

        ImageView imgView = (ImageView) dialog.findViewById(R.id.imageview);
        ImageView img_close = (ImageView) dialog.findViewById(R.id.img_close);

        if (flag == 1) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if (myBitmap != null)
                imgView.setImageBitmap(myBitmap);
            else {
                CommonUtils.showToast(activity, "Image not found");
                return;
            }
        } else {
            Glide.get(activity).clearMemory();
            Glide.with(activity)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(activity.getResources().getDrawable(R.drawable.truemeds_image))
                    .into(imgView);
        }

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);

        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setLayout(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
