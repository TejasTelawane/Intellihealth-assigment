package com.interview.truemedinterview.utils;

import android.os.Environment;
import android.util.Log;

import com.interview.truemedinterview.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;


public class MessageLogger {

    public static final String APP_ID = "DHB";
    public static String logDir = "/com.dhb";
    public static String logFileName = "/log.txt";
    public static boolean writeLogsToFile = false;
    public static final int LOG_LEVEL_VERBOSE = 5;
    public static final int LOG_LEVEL_WARNING = 4;
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_ERROR = 1;
    public static final int LOG_LEVEL_OFF = 0;
    public static final int CURRENT_LOG_LEVEL = LOG_LEVEL_VERBOSE;


    public static void log(String message, int logLevel) {

            try {
                String LoggerTag = "SBI Pension Seva";
                String printlogstr = "PrintLog: ";
                if (BuildConfig.DEBUG) {
                    if (logLevel == LOG_LEVEL_ERROR) {
                        Log.e(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_INFO) {
                        Log.i(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_DEBUG) {
                        Log.d(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_WARNING) {
                        Log.w(LoggerTag, printlogstr + "" + message);
                    }
                } else {
                    // Live
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (writeLogsToFile) {
                writeToFile(message);
            }
    }

    private static void writeToFile(String message) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + logDir);
            dir.mkdirs();
            File file = new File(dir, logFileName);
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                    new FileWriter(file, true), 8 * 1024));
            writer.println(APP_ID + " " + new Date().toString() + " : "
                    + message);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verbose(String message) {
        log(message, LOG_LEVEL_VERBOSE);
    }

    public static void debug(String message) {
        log(message, LOG_LEVEL_DEBUG);
    }

    public static void error(String message) {
        log(message, LOG_LEVEL_ERROR);
    }

    public static void info(String message) {
        log(message, LOG_LEVEL_INFO);
    }

    public static void warning(String message) {
        log(message, LOG_LEVEL_WARNING);
    }

    public static void msg(String message) {
        if (BuildConfig.DEBUG) {
            System.out.println(message);
        }
    }

}