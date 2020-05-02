package com.example.mobilitySupport.login;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginJSONTask extends AsyncTask<String, String, Void> {
    @Override
    protected Void doInBackground(String... strings) {

        HttpURLConnection con = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("http://127.0.0.1:3000/login");//연결을 함
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");//POST방식으로 보냄
            con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
            con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
