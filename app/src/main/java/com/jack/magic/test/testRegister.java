package com.jack.magic.test;

import android.content.Context;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jacktseng on 2015/10/7.
 */
public class testRegister implements Test {
    private static String TAG = testRegister.class.getSimpleName();

    String SERV_IP = "192.168.30.72";
    String SERV_URL = "http://" + SERV_IP + "/service_magic/_Register.php";

    Context mContext;

    private class User {
        String user_name = "POST";
        String user_password = "PW_POST";
        String user_device_id = "dsjfksfjkl222";
    }

    public testRegister(Context ctx) {
        mContext = ctx;
    }

    public void test(Context ctx) {

        new Thread() {
            public void run() {
                Looper.prepare();

                String response = register();
                Toast.makeText(mContext, "Server response for registering user: "
                        + response, Toast.LENGTH_SHORT).show();

            }
        }.start();
    }

    String register() {
        Log.i(TAG, "pass json: "  + new Gson().toJson(new User()));

        String userData = new Gson().toJson(new User());
        userData = Base64.encodeToString(userData.getBytes(), Base64.DEFAULT);
        Log.i(TAG, "base64: " + userData);

        String rtn = null;
        try {
            URL url = new URL(SERV_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(userData);
            writer.flush();
            writer.close();
            out.close();

            int rCode = conn.getResponseCode();
            Log.i(TAG, "response=" + rCode);

            if(rCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String data;
                while ((data = in.readLine()) != null) {
                    sb.append(data);
                }

                in.close();

                Log.e(TAG, "body=" + sb.toString());
                rtn = sb.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtn;
    }
}
