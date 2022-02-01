package com.interview.healthcare.customClass;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.interview.healthcare.R;
import com.interview.healthcare.utils.StringUtils;

public class CustomDialogs {

    public static OnAlertClickListener onAlertClickListener;
    public static OnOTPAlertClickListener onOTPAlertClickListener;

    public static void showCustomAlertWithPositiveButton(Activity activity, String strTitle, String strDescription, int icon, String btnName, OnAlertClickListener onAlertClickListener) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_with_positive_button);
        dialog.setCancelable(false);

        ImageView img_icon = (ImageView) dialog.findViewById(R.id.img_icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_description = (TextView) dialog.findViewById(R.id.tv_description);
        Button btn_positive = (Button) dialog.findViewById(R.id.btn_positive);

        StringUtils.setTextToTextView(tv_title,strTitle);
        StringUtils.setTextToTextView(tv_description,strDescription);
        StringUtils.setTextToButton(btn_positive,btnName);
        StringUtils.setImageToImageView(img_icon,icon);

        if (StringUtils.isNull(strDescription)){
            tv_description.setVisibility(View.GONE);
        }


        btn_positive.setOnClickListener(v -> {
            if (onAlertClickListener != null){
                onAlertClickListener.onPositiveButtonClicked();
            }
            dialog.dismiss();
        });

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.98);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);

//        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setLayout(width, FrameLayout.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setLayout(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    public static void showCustomAlertWithPositiveAndNegativeButton(Activity activity, String strTitle, String strDescription, int icon, String PositiveBtnName, String NegativeBtnName, final OnAlertClickListener onAlertClickListener) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_with_positive_and_negative_button);
        dialog.setCancelable(false);

        ImageView img_icon = (ImageView) dialog.findViewById(R.id.img_icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_description = (TextView) dialog.findViewById(R.id.tv_description);
        Button btn_positive = (Button) dialog.findViewById(R.id.btn_positive);
        Button btn_negative = (Button) dialog.findViewById(R.id.btn_negative);
        StringUtils.setTextToTextView(tv_title,strTitle);
        StringUtils.setTextToTextView(tv_description,strDescription);
        StringUtils.setTextToButton(btn_positive,PositiveBtnName);
        StringUtils.setTextToButton(btn_negative,NegativeBtnName);
        StringUtils.setImageToImageView(img_icon,icon);


        btn_positive.setOnClickListener(v -> {
            if (onAlertClickListener != null){
                onAlertClickListener.onPositiveButtonClicked();
            }
            dialog.dismiss();
        });

        btn_negative.setOnClickListener(v -> {
            if (onAlertClickListener != null){
                onAlertClickListener.onNegativeButtonClicked();
            }
            dialog.dismiss();
        });

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);

//        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setLayout(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }



    public static void showCustomOTPValidateDialogBox(Activity activity,  String title, OnOTPAlertClickListener onAlertClickListener) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_otp_validate_alert_box);
        dialog.setCancelable(false);

        ImageView img_icon = (ImageView) dialog.findViewById(R.id.img_icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        EditText edt_otp = (EditText) dialog.findViewById(R.id.edt_otp);
        Button btn_validate = (Button) dialog.findViewById(R.id.btn_validate);

        StringUtils.setTextToTextView(tv_title,title+"\nHint : 1234");

        img_icon.setOnClickListener(view -> dialog.dismiss());

        btn_validate.setOnClickListener(v -> {
            if (onAlertClickListener != null){
                onAlertClickListener.onValidateButtonClicked(edt_otp.getText().toString().trim());
            }
            dialog.dismiss();
        });

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.98);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);

        dialog.getWindow().setLayout(width, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void setOnAlertClickListener(OnAlertClickListener onAlertClickListener) {
        this.onAlertClickListener = onAlertClickListener;
    }

    public interface OnAlertClickListener  {
        void onPositiveButtonClicked();
        void onNegativeButtonClicked();
    }

    public void setOnOTPAlertClickListener(OnOTPAlertClickListener onOTPAlertClickListener) {
        this.onOTPAlertClickListener = onOTPAlertClickListener;
    }

    public interface OnOTPAlertClickListener  {
        void onValidateButtonClicked(String otp);
    }
}
