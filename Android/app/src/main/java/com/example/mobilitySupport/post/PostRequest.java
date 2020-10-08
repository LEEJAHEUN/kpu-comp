package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// 서버 요청 클래스
public class PostRequest {

    private String baseUrl = "http://121.168.1.81/";    // 기본 주소에 추가하여 사용
//    private String baseUrl = "http://192.168.81.240/";    // 기본 주소에 추가하여 사용

    private Map<String, String> parameters;
    private Context context;

    // 해당 프래그먼트의 context 보냄(getContext() 이용)
    public PostRequest(Context context){
        this.context = context;
    }

    // 장소 제보글 작성
    public void writePlace(String userID, String latitude, String longitude, String availability, String type,
                           String elevator, String wheel, View v, String url){
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);
        parameters.put("availability", availability);
        parameters.put("type", type);
        parameters.put("elevator", elevator);
        parameters.put("wheel", wheel);

        sendRequest(v, url);
    }

    //
    public void writeRoad(String userID, String latitude, String longitude, String availability, String type,
                          String stair, String roadBreakage, String slope, View v, String url) {
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);
        parameters.put("availability", availability);
        parameters.put("type", type);
        parameters.put("stair", stair);
        parameters.put("roadBreakage", roadBreakage);
        parameters.put("slope", slope);

        sendRequest(v, url);
    }

    public void sendRequest(final View v, final String requestUrl){
        String url = baseUrl + requestUrl;
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() { // 정상 응답
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                // 구별하고 싶으면 이부분에 조건문?
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("제보글 작성 완료!")
                                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(requestUrl.equals("postPlaceRegister.php")){
                                                    //페이지 이동
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
//                                                    Navigation.findNavController(v).navigate(R.id.action_fragment_writePlace_to_fragment_map);
                                                }
                                                else if(requestUrl.equals("postRoadRegister.php")){
                                                    //페이지 이동
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
//                                                    Navigation.findNavController(v).navigate(R.id.action_fragment_writeRoad_to_fragment_map);
                                                }
                                            }
                                        })
                                        .create().show();
                            }
                        } catch (Exception e){
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