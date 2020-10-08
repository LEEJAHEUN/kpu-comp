package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.example.mobilitySupport.StartActivity;
import com.example.mobilitySupport.member.MypageFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPostServerRequest extends AppCompatActivity {

    private String baseUrl = "http://121.168.1.81/";    // 기본 주소에 추가하여 사용
    //private String baseUrl = "http://192.168.81.240/";    // 기본 주소에 추가하여 사용

    private Map<String, String> parameters;
    private Context context;

    private static final String TAG_JSON="webnautes";
    private static final String TAG_postNUM = "postNum";
    private static final String TAG_writerID = "writerID";
    private static final String TAG_latitude = "latitude";
    private static final String TAG_longtitude ="longtitude";
    private static final String TAG_availability = "availability";
    private static final String TAG_tag = "tag";
    private static final String TAG_elevator = "elevator";
    private static final String TAG_wheelchairSlope = "wheelchairSlope";
    private static final String TAG_stair ="stair";
    private static final String TAG_roadBreakage = "roadBreakage";
    private static final String TAG_slope ="slope";

    // 해당 프래그먼트의 context 보냄(getContext() 이용)
    public MyPostServerRequest(Context context){
        this.context = context;
    }

    public void getMyPostPlace(String userID, View v, String url){ //특정 유저의 제보글(장소)
        parameters = new HashMap<>();
        parameters.put("userID", userID);

        sendPlaceRequest(v, url);
        /*
        myPostServerRequest myPostServerRequest = new myPostServerRequest(getContext());
        myPostServerRequest.getMyPostPlace(id, v, "readMyPostPlace.php");
         */
    }

    public void getMyPostRoad(String userID, View v, String url){//특정 유저의 제보글(길)
        parameters = new HashMap<>();
        parameters.put("userID", userID);

        sendRoadRequest(v, url);

        /*
        myPostServerRequest myPostServerRequest = new myPostServerRequest(getContext());
        myPostServerRequest.getMyPostRoad(id, v, "readMyPostRoad.php");
         */
    }

    public void getPostPlace(String latitude, String longitude, View v, String url){//제보글(장소)

/*        latitude = "37.339122699349126";
        longitude = "126.73280954360962";*/

        parameters = new HashMap<>();
        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);

        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            System.out.println("[key]:" + key + ", [value]:" + value);
        }
/*        System.out.println("getPostPlace 함수 내 latitude: "+ latitude);
        System.out.println("getPostPlace 함수 내 longitude: "+ longitude);*/

        sendPlaceRequest(v, url);

        /*
        myPostServerRequest myPostServerRequest = new myPostServerRequest(getContext());
        myPostServerRequest.getPostPlace(lat, lon, v, "readPostPlace.php");
         */
    }

    public void getPostRoad(String latitude, String longitude, View v, String url){ //제보글(길)
        parameters = new HashMap<>();

        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);

        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            System.out.println("[key]:" + key + ", [value]:" + value);
        }

/*        System.out.println("getPostRoad 함수 내 latitude: "+ latitude);
        System.out.println("getPostRoad 함수 내 longitude: "+ longitude);*/

        sendRoadRequest(v, url);

        /*
        myPostServerRequest myPostServerRequest = new myPostServerRequest(getContext());
        myPostServerRequest.getPostRoad(lat, lon, v, "readPostRoad.php");
         */
    }

    public void sendRoadRequest(final View v, final String requestUrl){
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

                            System.out.println("sendRoadRequest 내 jsonArray: "+ jsonArray);

                            if(jsonArray.length() > 0){
                                int length = jsonArray.length() * 9;
                                String[] roadList = new String[length];
                                int temp = 0;
                                ///////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject item = jsonArray.getJSONObject(i);
                                    //list에 값 저장
                                    roadList[temp] = item.getString(TAG_postNUM);
                                    roadList[temp+1] = item.getString(TAG_writerID);
                                    roadList[temp+2] = item.getString(TAG_availability);
                                    roadList[temp+3] = item.getString(TAG_tag);
                                    roadList[temp+4] = item.getString(TAG_slope);
                                    roadList[temp+5] = item.getString(TAG_stair);
                                    roadList[temp+6] = item.getString(TAG_roadBreakage);
                                    roadList[temp+7] = item.getString(TAG_latitude);
                                    roadList[temp+8] = item.getString(TAG_longtitude);

                                    System.out.println("서버에서 받아온lat: "+roadList[temp+7]);
                                    System.out.println("서버에서 받아온lon: "+roadList[temp+8]);

                                    temp = temp + 9;
                                }
                                if(requestUrl.equals("readPostRoad.php")){ // 마커 클릭 시 화면 이동
                                    Intent intent = new Intent(context, ManageRoadPostActivity.class);
                                    intent.putExtra("roadList", roadList);
                                    context.startActivity(intent);
                                }
                                if(requestUrl.equals("readMyPostRoad.php")){ // 자신의 도로 제보글 목록 프래그먼트로 이동

                                    Intent intent = new Intent(context, ManageRoadPostActivity.class); // 이동할 액티비티 지정
                                    intent.putExtra("roadList", roadList);
                                    context.startActivity(intent);
                                }
                            }
                            else{
                                System.out.println("서버에서 받아올 길 제보글 없음");
//                                Toast.makeText(context,"저장된 길 제보글이 없습니다.",Toast.LENGTH_SHORT).show();
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

    public void sendPlaceRequest(final View v, final String requestUrl){
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

                            System.out.println("sendPlaceRequest 내 jsonArray: "+ jsonArray);

                            if(jsonArray.length() > 0){
                                int length = jsonArray.length() * 8;
                                String[] placeList = new String[length];
                                int temp = 0;
                                ///////////////////////////////////////////////////////
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject item = jsonArray.getJSONObject(i);

                                    //순서 맞는지 확인
                                    //list에 값 저장
                                    placeList[temp] = item.getString(TAG_postNUM);
                                    placeList[temp+1] = item.getString(TAG_writerID);
                                    placeList[temp+2] = item.getString(TAG_availability);
                                    placeList[temp+3] = item.getString(TAG_tag);
                                    placeList[temp+4] = item.getString(TAG_elevator);
                                    placeList[temp+5] = item.getString(TAG_wheelchairSlope);
                                    placeList[temp+6] = item.getString(TAG_latitude);
                                    placeList[temp+7] = item.getString(TAG_longtitude);

                                    System.out.println("서버에서 받아온lat: "+placeList[temp+6]);
                                    System.out.println("서버에서 받아온lon: "+placeList[temp+7]);

                                    temp = temp+8;
                                }
                                if(requestUrl.equals("readPostPlace.php")){ // 마커 클릭 시 화면 이동 부분
                                    Intent intent = new Intent(context, ManagePlacePostActivity.class); // 이동할 액티비티 지정 필요
                                    intent.putExtra("placeList", placeList);
                                    context.startActivity(intent);
                                }
                                if(requestUrl.equals("readMyPostPlace.php")){ // 자신의 장소 제보글 목록 프래그먼트로 이동
                                    Intent intent = new Intent(context, ManagePlacePostActivity.class); // 이동할 액티비티 지정 필요
                                    intent.putExtra("placeList", placeList);
                                    context.startActivity(intent);
                                }
                            }
                            else{
                                System.out.println("서버에서 받아올 장소 제보글 없음");
//                                Toast.makeText(context,"저장된 장소 제보글이 없습니다.",Toast.LENGTH_SHORT).show();
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