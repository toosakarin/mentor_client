package com.jack.magic.test;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.jack.magic.conn.Request;
import com.jack.magic.conn.RequestListener;
import com.jack.magic.conn.Response;
import com.jack.magic.conn.form.DiscussionForm;
import com.jack.magic.conn.impl.HttpConnection;
import com.jack.magic.conn.impl.HttpRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jacktseng on 2015/10/12.
 */
public class testSend implements Test {
    public static final String TAG = testSend.class.getSimpleName();

    private String API = "_send.php";

    private Send mSend = new Send();

    private class Send {
        String user = "POST";
        String title = "test post discussion";
        int[] decks = {55, 1, 32, 11,78};
        String content = "this is a content section";
    }

    public void test(Context ctx) {
        if(testLogin.COOKIE == null) {
            Log.e(TAG, "please login first!");
            return;
        }
        new Thread() {
            public void run() {
//                send();
                send2();
            }
        }.start();
    }

    void send() {
        Log.i(TAG, "pass json: " + new Gson().toJson(new Send()));

        String userData = new Gson().toJson(new Send());
        userData = Base64.encodeToString(userData.getBytes(), Base64.DEFAULT);
        Log.i(TAG, "base64: " + userData);

        try {
            URL url = new URL(Test.SERV_HOST + API);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cookie", testLogin.COOKIE); //sets cookie
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
                String data = null;
                while ((data = in.readLine()) != null) {
                    sb.append(data);
                    Log.v(TAG, "readLine=" + data);
                }
                JSONObject response = new JSONObject(sb.toString());

                in.close();

                Log.e(TAG, "response=" + sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void send2() {
        DiscussionForm form = new DiscussionForm();
        form.user = mSend.user;
        form.title = mSend.title;
        form.decks = mSend.decks;
        form.content = mSend.content;

        try {
            URL url = new URL(Test.SERV_HOST + API);
            Request request = new HttpRequest(form.add());
            HttpConnection client = new HttpConnection();
            client.setURL(url);
            client.setCookie(testLogin.COOKIE);
            client.enableEncode(true);
            client.setRequestListener(new RequestListener<String>() {
                @Override
                public void onResponse(Response<String> response) {
                    if (response != null)
                        Log.d(TAG, "response status=" + response.getStatusCode());
                }
            });
            client.doRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
