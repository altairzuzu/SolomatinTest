package ru.fordexsys.solomatintest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

import ru.fordexsys.solomatintest.R;

public class NetworkUtil {

    /**
     * Returns true if the Throwable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof retrofit2.HttpException
                && ((retrofit2.HttpException) throwable).code() == statusCode;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String parseError(Throwable e) {
        String error;
        try {
            if (e instanceof UnknownHostException) {
                error = "UnknownHostException";
            } else if (e instanceof ConnectException) {
                error = "ConnectException";
            } else if (e instanceof NoSuchElementException) {
                error = "NoSuchElementException";
            } else {
                error = ((retrofit2.HttpException) e).response().errorBody().string();
            }
        } catch (Exception z) {
            error = "Exception";
        }
        return error;
    }

    public static String parseErrorString(Context context, String errString) {
        String error = "";
        if (!TextUtils.isEmpty(errString)) {
            switch (errString) {
                case "UnknownHostException":
                    error = context.getString(R.string.error_connection);
                    break;
                case "ConnectException":
                    error = context.getString(R.string.error_connection);
                    break;
                case "NoSuchElementException":
                    error = context.getString(R.string.error_no_such_element);
                    break;
                case "Exception":
                    error = context.getString(R.string.error_connection);
                    break;

                // auth
                case "default":
                    error = context.getString(R.string.error_connection);
                    break;

                default:
                    error = errString;
            }
        }
        return error;
    }

}