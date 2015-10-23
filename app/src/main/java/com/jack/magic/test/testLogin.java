package com.jack.magic.test;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.google.gson.Gson;
import com.jack.magic.conn.Request;
import com.jack.magic.conn.RequestListener;
import com.jack.magic.conn.Response;
import com.jack.magic.conn.form.LoginForm;
import com.jack.magic.conn.impl.HttpConnection;
import com.jack.magic.conn.impl.HttpRequest;
import com.jack.magic.gcm.GCMErrorListener;
import com.jack.magic.gcm.GCMProxy;
import com.jack.magic.gcm.GCMReceiveListener;
import com.jack.magic.gcm.GCMRegisterListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This test goals to do login with server, once the login request is sent, we will get the response
 * from server which are consist of some important data such as cookie and status code.
 */
public class testLogin implements Test {
    public static final String TAG = testLogin.class.getSimpleName();

    static String COOKIE;

    private final User mUser = new User();

    private String API = "_login.php";


    private class User {
        String username = "POST";
        String password = "PW_POST";
        String device_id = "ppppppwekjtngjengjkgnjk1124430";
    }

    public void test(final Context ctx) {

        new Thread() {
            public void run() {
//                registerToken(ctx);
                registerTokenMd(ctx);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                login();
                login2();
            }
        }.start();

    }

    void login() {
        Log.i(TAG, "pass json: " + new Gson().toJson(mUser));

        String userData = new Gson().toJson(mUser);
        userData = Base64.encodeToString(userData.getBytes(), Base64.DEFAULT);
        Log.i(TAG, "base64: " + userData);

        try {
            URL url = new URL(Test.SERV_HOST + API);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
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
                String data = null;
                while ((data = in.readLine()) != null) {
                    sb.append(data);
                    Log.v(TAG, "readLine=" + data);
                }
                JSONObject response = new JSONObject(sb.toString());

                in.close();

                Log.e(TAG, "response=" + sb.toString());

                /**
                 * Tries to get the cookie from server
                 */
                String cookie = conn.getHeaderField("Set-Cookie");
                Log.e(TAG, "cookie=" + cookie);

                COOKIE = cookie;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void login2() {
        LoginForm loginForm = new LoginForm();
        loginForm.username = mUser.username;
        loginForm.password = mUser.password;
        loginForm.device_id = mUser.device_id;

        try {
            URL url = new URL(Test.SERV_HOST + API);
            Request request = new HttpRequest(loginForm.add());
            HttpConnection client = new HttpConnection();
            client.setURL(url);
            client.enableEncode(true);
            client.setRequestListener(new RequestListener<String>() {
                @Override
                public void onResponse(Response<String> response) {
                    if (response != null)
                        COOKIE = response.getData("cookie");
                }
            });
            client.doRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void registerToken(Context ctx) {
        GCMRegistrar.checkDevice(ctx);
        GCMRegistrar.checkManifest(ctx);
        final String regId = GCMRegistrar.getRegistrationId(ctx);
        if(regId.equals("")) {
            GCMRegistrar.register(ctx, "107339576763");
        }
        else {
            Log.e(TAG, "regId=" + regId);
        }
        mUser.device_id = regId;
    }

    void registerTokenMd(Context ctx) {
        GCMProxy px = GCMProxy.getInstance();
        if(px != null) {
            px.setRegisterListener(new GCMRegisterListener() {
                @Override
                public void onRegistered(Context ctx, String s) {
                    Log.d(TAG, "onRegistered!");
                }

                @Override
                public void onUnregistered(Context ctx, String s) {
                    Log.d(TAG, "onUnregistered!");
                }
            });
            px.setErrorListener(new GCMErrorListener() {
                @Override
                public void onError(Context ctx, String s) {
                    Log.d(TAG, "onError!");
                }
            });
            px.setReceiveListener(new GCMReceiveListener() {
                @Override
                public void onMessage(Context ctx, Intent intent) {
                    String title = intent.getStringExtra("title");
                    String content = intent.getStringExtra("content");
                    Log.d(TAG, "onMessage()->title=" + title + " content=" + content );
                }
            });

            px.check(ctx);
            final String regId = px.getRegisterId(ctx);
            if(regId.equals("")) {
                px.register(ctx, "107339576763");
            }
            else {
                Log.e(TAG, "regId=" + regId);
            }
            mUser.device_id = regId;

        }

    }

}
