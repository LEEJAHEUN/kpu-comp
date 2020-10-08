package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class AllPostServerRequest extends AppCompatActivity {
    private String baseUrl = "http://121.168.1.81/";    // 기본 주소에 추가하여 사용
    //private String baseUrl = "http://192.168.81.240/";    // 기본 주소에 추가하여 사용

    private Map<String, String> parameters;
    private Context context;

    private static final String TAG_JSON="webnautes";
    private static final String TAG_latitude = "latitude";
    private static final String TAG_longtitude ="longitude";

    // 해당 프래그먼트의 context 보냄(getContext() 이용)
    public AllPostServerRequest(Context context){
        this.context = context;
    }

    public void getAllPost( View v, String url) { //전체 제보글
        sendRequest(v, url);

/*        allPostServerRequest allPostServerRequest = new allPostServerRequest(getContext());
        allPostServerRequest.getAllPostPlace(v, "getAllPost.php");*/
    }

    public void sendRequest(final View v, final String requestUrl){
        String url = baseUrl + requestUrl;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() { // 정상 응답
                    @Override
                    public void onResponse(String mJsonString) {

                        try{
                            JSONObject jsonObject = new JSONObject(mJsonString);
                            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                            System.out.println("전체 제보글 받아옴:"+ jsonArray);

                            int length = jsonArray.length() * 2;
                            String[] allPostList = new String[length];
                            int temp = 0;

                            ///////////////////////////////////////////////////////
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject item = jsonArray.getJSONObject(i);
                                //list에 값 저장
                                allPostList[temp] = item.getString(TAG_latitude);
                                allPostList[temp + 1] = item.getString(TAG_longtitude);

                                temp = temp + 2;
                            }

                            if(requestUrl.equals("getAllPost.php")){ // 시작 시 전체 제보글의 위치를 받음

                                Intent intent = new Intent(context, MainActivity.class); // 이동할 액티비티 지정
                                //이전 액티비티 삭제(뒤로가기 시 이전 액티비티 표시하지 않음)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("allPostList", allPostList);

                                System.out.println("allPostList:"+allPostList);

                                context.startActivity(intent);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { } // 에러 발생
                }){
            @Override
            protected Map<String, String> getParams() {
                return parameters;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
    }
}