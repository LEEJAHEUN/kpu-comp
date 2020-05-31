package com.example.mobilitySupport.join;

import android.os.AsyncTask;

import com.example.mobilitySupport.data.JoinData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinJSONTask extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... urls) {

        HttpURLConnection con = null;
        BufferedReader reader = null;
        OutputStream outputStream = null;
        String line = null;
        JoinData joinData = null;


        try {
            JSONObject jsonObject = new JSONObject();
            /*
            jsonObject.accumulate("userID", joinData.getUserID());
            jsonObject.accumulate("userPW", joinData.getUserPW());
            jsonObject.accumulate("userMail", joinData.getUserEmail());
            jsonObject.accumulate("userType", joinData.getUserType());
            */

            jsonObject.accumulate("userID", "HJ");
            jsonObject.accumulate("userPW", "HJ");
            jsonObject.accumulate("userMail", "HJ");
            jsonObject.accumulate("userType", "HJ");


            //URL url = new URL(urls[0]);//연결을 함
            URL url = new URL("http://127.0.0.1:3000/signup");
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");//POST방식으로 보냄
            con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
            con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
            con.setRequestProperty("Accept", "text/html");

            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();

            outputStream = con.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();

            InputStream stream = con.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            while((line = reader.readLine())!= null){
                buffer.append(line);
            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(con!=null)
                con.disconnect();
            try{
                if(reader!=null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
