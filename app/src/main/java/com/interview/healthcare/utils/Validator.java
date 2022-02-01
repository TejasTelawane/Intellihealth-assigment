package com.interview.healthcare.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static Boolean isValidEmail(String email) {
        if (StringUtils.isNull(email)) {
            return false;
        }
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }


    public static Boolean doesItHaveSpecialCharacter(String val) {
        Pattern pattern;
        Matcher matcher;
        final String _PATTERN = "[a-zA-Z. ]*";
        pattern = Pattern.compile(_PATTERN);
        matcher = pattern.matcher(val);
        return matcher.matches();

    }

    public static boolean isValidPincode(String pincode) {

        if (StringUtils.isNull(pincode) || pincode.startsWith("0") || pincode.startsWith("9"))
            return false;

        if (pincode.length() != 6)
            return false;

        return true;
    }

    public static boolean isValidMobile(String phone1) {

        if (StringUtils.isNull(phone1)) {
            return false;
        }
        String phone = phone1.trim();
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() == 10) {
                if (phone.startsWith("5") || phone.startsWith("4") || phone.startsWith("3") || phone.startsWith("2") || phone.startsWith("1") || phone.startsWith("0")) {
                    check = false;
                } else {
                    check = true;
                }
            } else {
                check = false;
            }
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isValidUserName(String username) {
        if (StringUtils.isNull(username)) {
            return false;
        }
        if (username.trim().length() < 2 || username.trim().length() > 50) {
            return false;
        }
        return true;
    }

    public static boolean isValidUserID(String username) {
        if (StringUtils.isNull(username)) {
            return false;
        }
        if (username.trim().length() < 5 || username.trim().length() > 15) {
            return false;
        }
        return true;
    }


    public static boolean isValidFirstName(String name) {
        if (StringUtils.isNull(name))
            return false;

        if (name.trim().length() < 2 || name.trim().length() > 30)
            return false;

        return true;
    }

    public static boolean isValidMiddleName(String name) {
        if (StringUtils.isNull(name))
            return false;

        if (name.trim().length() < 1 || name.trim().length() > 30)
            return false;

        return true;
    }

    public static boolean isValidLastName(String name) {
        if (StringUtils.isNull(name))
            return false;

        if (name.trim().length() < 1 || name.trim().length() > 30)
            return false;

        return true;
    }


    public static boolean isValidLocationName(String name) {
        if (StringUtils.isNull(name))
            return false;

        if (name.trim().length() < 2 || name.trim().length() > 40)
            return false;

        return true;
    }


    public static boolean isValidAddress(String Address) {
        if (StringUtils.isNull(Address))
            return false;

        if (Address.trim().length() < 25 || Address.trim().length() > 250)
            return false;

        return true;
    }


    public static boolean Check_Startswith_Specialcharecter(String stringtoCheck) {

        Pattern p = Pattern.compile("(^\\p{Punct})");
        Matcher m = p.matcher(stringtoCheck);
        boolean a = m.find();
        if (a)
            System.out.println("Password must contain at least one special character at the beginning or end!");
        else
            System.out.println("....output..." + a);
        return a;
    }

    public static int LongestStringSequence(String message) {

        int largestSequence = 0;
        char longestChar = '\0';
        int currentSequence = 1;
        char current = '\0';
        char next = '\0';

        for (int i = 0; i < message.length() - 1; i++) {
            current = message.charAt(i);
            next = message.charAt(i + 1);

            // If character's are in sequence , increase the counter
            if (current == next) {
                currentSequence += 1;
            } else {
                if (currentSequence > largestSequence) { // When sequence is
                    // completed, check if
                    // it is longest
                    largestSequence = currentSequence;
                    longestChar = current;
                }
                currentSequence = 1; // re-initialize counter
            }
        }
        if (currentSequence > largestSequence) { // Check if last string
            // sequence is longest
            largestSequence = currentSequence;
            longestChar = current;
        }

        System.out.println("Longest character sequence is of character "
                + longestChar + " and is " + largestSequence + " long");

        return largestSequence;


    }

    public static boolean isValidRemarks(String remarks) {
        if (StringUtils.isNull(remarks))
            return false;

        if (remarks.trim().length() < 25 || remarks.trim().length() > 250)
            return false;

        return true;
    }


    public static boolean isvalidLocation(double mLatitude, double mLongitude) {

        if (mLatitude != 0.0 && mLongitude != 0.0 && mLatitude != 0 && mLongitude != 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidOTP(String OTP) {
        if (StringUtils.isNull(OTP))
            return false;

        if (OTP.trim().length() != 6)
            return false;


        return true;
    }

    public static boolean isValidPassword(String strpassword) {
        if (StringUtils.isNull(strpassword))
            return false;

        if (strpassword.trim().length() < 8 || strpassword.trim().length() > 12)
            return false;

        Matcher matcher = Pattern.compile(Constants.Password_REGEXPATTERN).matcher(strpassword);
        if (!matcher.matches()){
            return false;
        }

        return true;
    }

    public static boolean isValidAccountNumber(String strAccountNumber) {
        if (StringUtils.isNull(strAccountNumber))
            return false;

        if (strAccountNumber.trim().length() != 11)
            return false;

        return true;
    }

    public static boolean isPasswordMatching(String ConfirmPassword, String Password) {
        return StringUtils.CheckEqualCaseSensitive(ConfirmPassword,Password);
    }

    public static boolean isValidMPin(String Mpin){
        if (StringUtils.isNull(Mpin))
            return false;

        if (Mpin.trim().length() != 6)
            return false;

        return true;
    };

    public static boolean isValidFeedback(String StrFeedback) {
        if (StringUtils.isNull(StrFeedback))
            return false;

        if (StrFeedback.trim().length() < 20 || StrFeedback.trim().length() > 200)
            return false;

        return true;
    }
}