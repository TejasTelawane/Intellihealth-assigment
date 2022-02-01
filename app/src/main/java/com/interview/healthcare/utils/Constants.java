package com.interview.healthcare.utils;

public class Constants {
    public static long ResndOTPTimeout = 61000;
    public static String HEADER_USER_AGENT = "Header-Agent";

    public static String Password_REGEXPATTERN = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[!@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{8,16}" +               //at least 8 characters and max 16
            "$";
    public static String PleaseEnterFirstName = "Please enter First name";
    public static String PleaseEnterValidFirstName = "Please enter valid First name (Length req Min: 2 and Max: 30)";
    public static String PleaseEnterLastName = "Please enter Last name";
    public static String PleaseEnterValidLastName = "Please enter valid Last name (Length req Min: 2 and Max: 30)";
    public static String PleaseEnterMobileno = "Please enter Mobile no";
    public static String PleaseEnterValidMobileno = "Please enter valid Mobile no (Length req 10)";
    public static String PleaseEnterEmail = "Please enter Email Id";
    public static String PleaseEnterValidEmail = "Please enter valid Email Id";
    public static String PleaseEnterPassword = "Please enter password";
    public static String PleaseEnterValidPassword = "Please enter valid password (Length req Min: 8 and Max: 16) and Should have 1 uppercase and 1 lowercase character, 1 special character and 1 digit";
    public static String PasswordshouldMatch = "Password and Confirm Password are not matching";

}
