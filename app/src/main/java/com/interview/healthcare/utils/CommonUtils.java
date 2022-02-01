package com.interview.healthcare.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;
import com.interview.healthcare.BuildConfig;
import com.interview.healthcare.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

    public static void showToast(Activity context, String message) {
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

    public  static File CreateDirectoryPathifNotExists(String FolderName){
        File dir = null ;

        dir = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+ "/"+FolderName);

        // Make sure the path directory exists.
        if (!dir.exists()) {
            // Make it, if it doesn't exit
            boolean success=dir.mkdirs();
            if(!success) {dir=null;}
        }
        return  dir ;
    }

    public static void DownloadFile(Activity mActivity, final String imageURL, final String fileName) {

        try {
            showProgressDialog(mActivity,"","",false);
            GlideUrl glideUrl = new GlideUrl(imageURL, new LazyHeaders.Builder()
                    .addHeader(Constants.HEADER_USER_AGENT, "")
                    .build());
            Glide.with(mActivity)
                    .asBitmap()
                    .load(glideUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            try {
                                ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
                                if (resource != null) {
                                    resource.compress(Bitmap.CompressFormat.JPEG, 100, bytes1);
                                    saveImage(mActivity,resource,fileName);
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

    private static String saveImage(Activity mActivity, Bitmap image, String fileName) {
        String savedImagePath = null;

        String imageFileName = fileName + ".jpg";
        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/HealthCare");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(mActivity,savedImagePath);

            openImageFile(mActivity, imageFile);
            Toast.makeText(mActivity, "IMAGE SAVED at location : "+storageDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
            CommonUtils.hideProgressDialog(mActivity);
        }
        return savedImagePath;
    }

    private static void openImageFile(Activity mActivity, File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(FileProvider.getUriForFile(mActivity.getApplicationContext(), mActivity.getApplicationContext().getPackageName() + ".imageprovider", imageFile), "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mActivity.startActivity(intent);
    }

    private static void galleryAddPic(Activity mActivity, String imagePath) {
        hideProgressDialog(mActivity);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
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

    public static void DownloadImageUsingGlideAndShare(Activity mActivity, String Imageurl, String msgtoSend){
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
                                    String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), resource, "HealthCare_" + System.currentTimeMillis(), null);
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
