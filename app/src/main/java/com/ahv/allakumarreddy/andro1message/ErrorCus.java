package com.ahv.allakumarreddy.andro1message;

import android.util.Log;

/**
 * Created by alla.kumarreddy on 5/15/2017.
 */

public class ErrorCus {
    private String errorMsg;
    private static String errorData = "";

    public ErrorCus(String errorMsg) {
        this.errorMsg = errorMsg;
        errorData += this.errorMsg + "\n";
        Log.d("ahv_log", errorMsg);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static String getErrorData() {
        return errorData;
    }

    public static void setErrorData(String errorData) {
        ErrorCus.errorData = errorData;
    }
}
