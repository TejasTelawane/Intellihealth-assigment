package com.interview.healthcare.utils;

import android.Manifest;
import android.app.Activity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.interview.healthcare.R;

import java.util.List;

public class PermissionUtils {

    private static OnPermissionSuccessListener onPermissionSuccessListener;

    public static void AsKPermissionatSplashScreen(final Activity activity, PermissionListener permissionListener) {
        TedPermission.with(activity)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .setRationaleConfirmText("OK")
                .setDeniedMessage(activity.getString(R.string.permissionRejectedMsg))
                .setPermissionListener(permissionListener)
                .check();
    }



    public static void AsKPermissionToCall(final Activity activity,final OnPermissionSuccessListener permissionListener) {
        TedPermission.with(activity)
                .setPermissions( Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_NETWORK_STATE)
                .setRationaleMessage(activity.getString(R.string.permissionToCallMsg) )
                .setRationaleConfirmText("OK")
                .setDeniedMessage(activity.getString(R.string.permissionRejectedMsg))
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (permissionListener != null){
                            permissionListener.onPermissionGranted();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        CommonUtils.showToast(activity, activity.getString(R.string.permissionDenied) + deniedPermissions.toString());
                    }
                })
                .check();
    }


    public static void AsKPermissionToDownloadFile(final Activity activity,final OnPermissionSuccessListener permissionListener) {
        TedPermission.with(activity)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setRationaleMessage(activity.getString(R.string.permissionToDownloadFile))
                .setRationaleConfirmText("OK")
                .setDeniedMessage(activity.getString(R.string.permissionRejectedMsg))
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (permissionListener != null){
                            permissionListener.onPermissionGranted();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        CommonUtils.showToast(activity, activity.getString(R.string.permissionDenied) + deniedPermissions.toString());
                    }
                })
                .check();
    }

    public static void AsKPermissionToDownloadAndShareImage(final Activity activity,final OnPermissionSuccessListener permissionListener) {
        TedPermission.with(activity)
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setRationaleMessage(activity.getString(R.string.strPermissionToDownloadImage))
                .setRationaleConfirmText("OK")
                .setDeniedMessage(activity.getString(R.string.permissionRejectedMsg))
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (permissionListener != null){
                            permissionListener.onPermissionGranted();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        CommonUtils.showToast(activity, activity.getString(R.string.permissionDenied) + deniedPermissions.toString());
                    }
                })
                .check();
    }

    public static void AsKPermissionToDownloadImage(final Activity activity,final OnPermissionSuccessListener permissionListener) {
        TedPermission.with(activity)
                .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setRationaleMessage("We need permission to download image.")
                .setRationaleConfirmText("OK")
                .setDeniedMessage(activity.getString(R.string.permissionRejectedMsg))
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (permissionListener != null){
                            permissionListener.onPermissionGranted();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        CommonUtils.showToast(activity, activity.getString(R.string.permissionDenied) + deniedPermissions.toString());
                    }
                })
                .check();
    }



    public void setOnPermissionSuccessListener(OnPermissionSuccessListener onPermissionSuccessListener) {
        this.onPermissionSuccessListener = onPermissionSuccessListener;
    }

    public interface OnPermissionSuccessListener  {
        void onPermissionGranted();
    }
}
