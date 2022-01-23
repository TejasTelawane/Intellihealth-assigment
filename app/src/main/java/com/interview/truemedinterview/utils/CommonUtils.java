package com.interview.truemedinterview.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;
import com.interview.truemedinterview.BuildConfig;
import com.interview.truemedinterview.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class CommonUtils {

    private Context context;
    static AppCompatDialog progressDialog;


    public CommonUtils(Context context) {
        this.context = context;
    }

    public static String getDeviceIMEI(Activity mActivity) {
        String deviceUniqueIdentifier = null;
        try {
            deviceUniqueIdentifier = Settings.Secure.getString(mActivity.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MessageLogger.msg("ANdroid ID : "+deviceUniqueIdentifier);
        return deviceUniqueIdentifier;
    }

    public static String getDeviceIMEI(Context context) {
        String deviceUniqueIdentifier = null;
        try {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceUniqueIdentifier;
    }

    public static boolean isWhatsApp(Activity mActivity) {
        try {
            PackageManager pm = mActivity.getPackageManager();
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            showToast(mActivity, "WhatsApp not installed.");

        }
        return false;
    }

    public static URL buildWhatsappUrl(Uri myUri) {
        URL finalUrl = null;
        try {
            finalUrl = new URL(myUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showSnackbar(Context context,View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showProgressDialog(Activity activity, String title, String msg, boolean cancelable) {

        progressDialog = new AppCompatDialog(activity);
//        progressDialog.setTitle(title);
//        progressDialog.setMessage(msg);
//        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(cancelable);
        progressDialog.setCancelable(cancelable);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.custom_loader_dialog);

        try {
            if (progressDialog != null && !progressDialog.isShowing())

                if (!activity.isFinishing()) {
                    progressDialog.show();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(Activity activity) {

        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                if (!activity.isFinishing()) {
                    progressDialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DisplayEnviorment(Activity mActivity, TextView tvStagingMsg) {
        if (BuildConfig.DEBUG) {
            tvStagingMsg.setText("Debug_" + CommonUtils.getCurrentAppVersionName(mActivity));
        } else {
            tvStagingMsg.setVisibility(View.GONE);
        }
    }

    public static String getCurrentAppVersionName(Context pContext) {
        String versionName = "";
        try {
            PackageInfo packageInfo = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getAndroidId(Context pContext) {
        String imeiNo = "";
        try {
            imeiNo = Settings.Secure.getString(pContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiNo;
    }

    public static String covertTimeToText(long time) {

        String convTime = null;

        String prefix = "";
        String suffix = "";

        long second = TimeUnit.MILLISECONDS.toSeconds(time);
        long minute = TimeUnit.MILLISECONDS.toMinutes(time);
        long hour   = TimeUnit.MILLISECONDS.toHours(time);
        long day  = TimeUnit.MILLISECONDS.toDays(time);

        if (second < 60) {
            convTime = second + " sec "+suffix;
        } else if (minute < 60) {
            if (minute == 1){
                convTime = minute + " min "+suffix;
            }else{
                convTime = minute + " mins "+suffix;
            }

        } else if (hour < 24) {
            if (hour == 1){
                convTime = hour + " hr "+suffix;
            }else{
                convTime = hour + " hrs "+suffix;
            }

        } else if (day >= 7) {
            if (day > 360) {
                convTime = (day / 360) + " yrs " + suffix;
            } else if (day > 30) {
                convTime = (day / 30) + " months " + suffix;
            } else {
                convTime = (day / 7) + " weeks " + suffix;
            }
        } else if (day < 7) {
            if (day == 1){
                convTime = day + " day "+suffix;
            }else{
                convTime = day+" days "+suffix;
            }

        }

        return convTime;
    }

    public static void shareonWhatsApp(Activity activity,String msgtoSend) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msgtoSend);
        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(sendIntent);
    }

    public static void DownloadImageUsingGlide(Activity mActivity, String Imageurl, String msgtoSend){
        try {
            CommonUtils.showProgressDialog(mActivity,"","",false);
            GlideUrl glideUrl = new GlideUrl(Imageurl, new LazyHeaders.Builder()
                    .addHeader(Constants.HEADER_USER_AGENT, "")
                    .build());
            Glide.with(mActivity)
                    .asBitmap()
                    .load(glideUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            CommonUtils.hideProgressDialog(mActivity);
                            try {
                                ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
                                if (resource != null) {
                                    resource.compress(Bitmap.CompressFormat.JPEG, 100, bytes1);
                                    String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), resource, "TrueMeds_" + System.currentTimeMillis(), null);
                                    if (!StringUtils.isNull(path)) {
                                        Uri uri = Uri.parse(path);
                                        shareOnWhatsapp(mActivity,msgtoSend, uri);
                                    } else {
                                        shareOnWhatsapp(mActivity,msgtoSend, null);
                                    }

                                } else {
                                    shareOnWhatsapp(mActivity,msgtoSend, null);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtils.hideProgressDialog(mActivity);
        }
    }

    public static void shareOnWhatsapp(Activity activity, String textBody, Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
//        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, !TextUtils.isEmpty(textBody) ? textBody : "");

        if (fileUri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
        }
        try {
            activity.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            CommonUtils.showToast(activity,"Error: sharing failed");
        }
    }

}
