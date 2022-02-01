package com.interview.healthcare.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Collection;

/**
 * Created by amit singh tomar on 28/07/2017.
 */

public class StringUtils {


    public static boolean FlagForrefresh=false;

    public static ActionMode.Callback CallbackTODisableCopyPasteInEditText = new ActionMode.Callback() {

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            menu.clear();
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            if (menu != null) {
                menu.removeItem(android.R.id.paste);
            }
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };

    //Download PDF with implicit downloader (ACTION_VIEW).
    public static void downloadPDF(Context context, String URL) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
    }

    // To remove last characters from string
    public static String removeLastCharacter(String str) {
        if (str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    // To replace the mobile string with star
    public static String replaceMobileNumberwithstar(String text, int DigitCountToShowAtEnd) {
        return text.replaceAll("\\w(?=\\w{"+DigitCountToShowAtEnd+"})", "*");
    }

    // To replace the email string with star
    public static String replaceEmailIDwithstar(String text, int DigitCountToShowAtStart) {
        return text.replaceAll("(?<=.{"+DigitCountToShowAtStart+"}).(?=[^@]*?@)", "*");
    }

    public static String replaceTextWithStar_ShowStart(String text, int DigitCountToShowAtStart) {
        return text.replaceAll("(?<=.{"+DigitCountToShowAtStart+"}).(?=[^@]*)", "*");
    }

    public static String replaceTextWithStar_ShowEnd(String text, int DigitCountToShowAtEnd) {
        return text.replaceAll("\\w(?=\\w{"+DigitCountToShowAtEnd+"})", "*");
    }

    public static String removeLeadingZero(String data) {
        if (!isNull(data)){
            data.replaceFirst("^0+(?!$)", "");
        }else{
            data = "";
        }

        return data;
    }

    public static String replaceAllSpecialCharecters(String str) {
        String newStr = "";
        if (!isNull(str)){
            newStr = str.replaceAll("[^A-Za-z0-9]","");
        }

        return newStr;
    }

    public String checkJsonNullStringValue(JSONObject jsonObject, String key) {
        String value = "";
        try {
            value = jsonObject.isNull(key) ? "" : jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toUpperCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToUpperCase = Character.toUpperCase(currentChar);
            result = result + currentCharToUpperCase;
        }
        return result;
    }

    public static String toLowerCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result = result + currentCharToLowerCase;
        }
        return result;
    }

    public static String toSentenceCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public static float parseRatioToFloat(String ratio, String regex) {
        if (ratio.contains(regex)) {
            String[] rat = ratio.split(regex);
            return Float.parseFloat(rat[0]) / Float.parseFloat(rat[1]);
        } else {
            return Float.parseFloat(ratio);
        }
    }

    public static void setTextToTextView(TextView textview, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (textview != null) {
                textview.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextToButton(Button button, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (button != null) {
                button.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setImageToImageView(ImageView imageView, int drawable) {
        try {

            if (imageView != null) {
                imageView.setImageResource(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextToTextView(TextView textview, CharSequence msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (textview != null) {
                textview.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHTMLTextToTextView(TextView textview, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (textview != null) {
                textview.setText(Html.fromHtml(msg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextToTextView(EditText edttext, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (edttext != null) {
                edttext.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNull(String val){
        if(val==null||val.equalsIgnoreCase(null)||val.trim().equalsIgnoreCase("")||val.trim().equalsIgnoreCase("null")|| val.trim()==""||val.trim()=="null")
            return true;
        return false;
    }

    public static boolean isNull(Collection obj) {
        if (obj == null || obj.size() == 0){
            return true;
        }
        return false;
    }

    public static boolean isNull(Object obj) {
        if (obj == null){
            return true;
        }
        return false;
    }

    public static int parseInt(String strnumber) {
        int finalNumber = 0 ;
        if (!isNull(strnumber)){
            try {
                finalNumber = Integer.parseInt(strnumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return finalNumber;
    }

    public static float parseFloat(String strnumber) {
        float finalNumber = 0 ;
        if (!isNull(strnumber)){
            try {
                finalNumber = Float.parseFloat(strnumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return finalNumber;
    }

    public static double parseDouble(String strnumber) {
        double finalNumber = 0 ;
        if (!isNull(strnumber)){
            try {
                finalNumber = Double.parseDouble(strnumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return finalNumber;
    }

    public static String parseString(int number) {
        String finalString = "" ;
        try {
            finalString = String.valueOf(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return finalString;
    }

    public static String parseString(double number) {
        String finalString = "" ;
        try {
            finalString = String.valueOf(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return finalString;
    }

    public static boolean CheckEqualIgnoreCase(String str1, String str2) {
        if (!isNull(str1) && !isNull(str2) && str1.equalsIgnoreCase(str2)){
            return true;
        }
        return false;
    }

    public static boolean CheckEqualCaseSensitive(String str1, String str2) {
        if ( str1 != null  && str2 != null && str1.equals(str2)){
            return true;
        }
        return false;
    }

    public static boolean CheckifStringContains(String mainString, String StrToCheck) {
        if (!isNull(mainString) && !isNull(StrToCheck) && mainString.toUpperCase().contains(StrToCheck.toUpperCase())){
            return true;
        }
        return false;
    }


    public static final String[] units = {"Zero", "One", "Two", "Three", "Four",
            "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen"};

    public static final String[] tens = {
            "Zero",        // 0
            "Ten",        // 1
            "Twenty",    // 2
            "Thirty",    // 3
            "Forty",    // 4
            "Fifty",    // 5
            "Sixty",    // 6
            "Seventy",    // 7
            "Eighty",    // 8
            "Ninety"    // 9
    };

    public static String ConvertNumbertoWords( int number) {
        if (number == 0)
            return "ZERO";
        if (number < 0)
            return "minus " + ConvertNumbertoWords(Math.abs(number));
        String words = "";

        if ((number / 1000000000) > 0)
        {
            words += ConvertNumbertoWords(number / 1000000000) + " BILLION ";
            number %= 1000000000;
        }

        if ((number / 10000000) > 0)
        {
            words += ConvertNumbertoWords(number / 10000000) + " CRORE ";
            number %= 10000000;
        }

        if ((number / 100000) > 0)
        {
            words += ConvertNumbertoWords(number / 100000) + " LAKH ";
            number %= 100000;
        }

        if ((number / 1000) > 0)
        {
            words += ConvertNumbertoWords(number / 1000) + " THOUSAND ";
            number %= 1000;
        }
        if ((number / 100) > 0)
        {
            words += ConvertNumbertoWords(number / 100) + " HUNDRED ";
            number %= 100;
        }
        if (number > 0)
        {
            if (words != "")
                words += "AND ";
            String[] unitsMap = { "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN" };
            String[] tensMap =  { "ZERO", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY" };

            if (number < 20)
                words += unitsMap[number];
            else
            {
                words += tensMap[number / 10];
                if ((number % 10) > 0)
                    words += " " + unitsMap[number % 10];
            }
        }
        return words;
    }

    public static String GetAmountInIndianCurrency(int amount) {

        String amountInIndiaCurrency = "0";

        try {
            amountInIndiaCurrency = new DecimalFormat("##,##,##,##,##,###").format(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amountInIndiaCurrency;
    }

}
