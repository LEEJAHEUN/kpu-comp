package com.example.mobilitySupport.findRoute;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.skt.Tmap.TMapPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class PostLocationRequest {

    private static final String TAG_JSON = "list";
    private static final String TAG_latitude = "latitude";
    private static final String TAG_longitude = "longitude";
    //private static final String TAG_DUPLICATE  = "duplicate";

    private String baseUrl = "http://121.168.1.81/";    // 기본 주소에 추가하여 사용
    //private String baseUrl = "http://192.168.81.240/";    // 기본 주소에 추가하여 사용
    private Context context;
    private Map<String, String> parameters;
    private MainActivity activity;
    private TMapPoint start_point, end_point;
    private double distance;
    CardView rootHistory;
    ProgressBar progressBar;

    //private String postLocation = "";  // 제보글 좌표 목록
    ArrayList<TMapPoint> postLocation = new ArrayList<>();
    ; // 제보글 좌표 목록
    ArrayList<String> postID = new ArrayList<>();
    ;  // 제보글 id 목록

    int[] searchOption = {10, 4, 0, 30};    // 검색 옵션
    //String passList = "";
    private String receiveMsg = "";
    private List<TMapPoint> list = Collections.synchronizedList(new ArrayList<TMapPoint>());
    ArrayList<String> coordinateID;
    ;  // 제보글 id 목록
    //private String postName = "";

    JSONArray coordinateArray = null;
    String test = "";
    StringTokenizer stringTokenizer;
    double x1 = 0, y1 = 0;
    String x2 = "", y2 = "";
    int i = 0;

    public PostLocationRequest(Context context, MainActivity activity, TMapPoint start_point, TMapPoint end_point, double distance,
                               CardView rootHistory, ProgressBar progressBar) {
        this.context = context;
        this.activity = activity;
        this.start_point = start_point;
        this.end_point = end_point;
        this.distance = distance;
        this.rootHistory = rootHistory;
        this.progressBar = progressBar;
        Log.d("거리 test", String.valueOf(distance));
        parameters = new HashMap<>();
        String post = start_point.getLongitude() + "," + start_point.getLatitude()
                + "," + end_point.getLongitude() + "," + end_point.getLatitude() + "," + distance;
        parameters.put("postLocation", post);
    }

    public void sendPostLocation(String requestUrl) {
        String url = baseUrl + requestUrl;
        postID.clear();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() { // 정상 응답
                    @Override
                    public void onResponse(String mJsonString) {
                        try {
                            JSONObject jsonObject = new JSONObject(mJsonString);
                            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                            //ArrayList<TMapPoint> postLocation = new ArrayList<>(); // 제보글 좌표 목록

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                String longitude = item.getString(TAG_longitude);
                                String latitude = item.getString(TAG_latitude);

                                TMapPoint post = new TMapPoint(item.getDouble(TAG_longitude), item.getDouble(TAG_latitude));
                                postLocation.add(post);
                                postID.add(getLinkID(latitude, longitude));
                            }

                                for(int j = 0; j<postID.size();j++){
                                    Log.d("제보글 목록", postID.get(j).toString());
                                }
                            getAvoidRoute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    } // 에러 발생
                }) {

            @Override
            protected Map<String, String> getParams() {
                return parameters;
            }

        };
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
    }

    public String getLinkID(final String lat, final String lon) {
        String linkID = null;
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url;
                    String str = "";
                    url = new URL("https://apis.openapi.sk.com/tmap/road/nearToRoad?version=1&format=json&opt=2" +
                            "&appKey=" + context.getResources().getString(R.string.apiKey) + "&lat=" + lat
                            + "&lon=" + lon);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    conn.setDoOutput(true);

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        reader.close();
                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start(); // Thread 실행
        try {
            mThread.join();
            JSONObject object = new JSONObject(receiveMsg).getJSONObject("resultData").getJSONObject("header");
            linkID = object.getString("linkId") + object.getString("idxName");

        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return linkID;
    }

    public void getAvoidRoute() {
        Boolean isInclude = false;              // 제보글 포함 여부
        String passList = "";

        for (int i = 0; i < 4; i++) {  // i < 5
            makeRoute(searchOption[i], "");
            isInclude = checkRoute();

            if (i == 4) {
                // passList 생성 함수
                //passList = getPassList();
                //isInclude = checkRoute(searchOption[0], passList);
            }
            if (isInclude.equals(true)) {
                // 경로 출력 함수
                Log.d("겹치는 경로 없음", "와아");
                postID.clear();
                ArrayList<TMapPoint> startEnd = new ArrayList<>();
                startEnd.add(start_point); startEnd.add(end_point);

                rootHistory.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                activity.getAvoidPath(list, startEnd);

                return;
            }

            list.clear();
        }

        if (isInclude.equals(false)) {
            //rootHistory.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            Log.d("겹치는 경로", "있음");
            postID.clear();
            Toast.makeText(activity.getApplicationContext(), "회피 경로를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
        }
    }

    public void makeRoute(final int searchOption, final String passList) {
        coordinateID = new ArrayList<>();
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url;
                    String str = "";

                    if (passList.equals("")) {
                        url = new URL("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json" +
                                "&appKey=" + context.getResources().getString(R.string.apiKey) + "&startX=" + Double.toString(start_point.getLongitude())
                                + "&startY=" + Double.toString(start_point.getLatitude()) + "&endX=" + Double.toString(end_point.getLongitude()) +
                                "&endY=" + Double.toString(end_point.getLatitude()) + "&startName=start&endName=end" +
                                "&searchOption=" + searchOption);
                    } else
                        url = new URL("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json" +
                                "&appKey=" + context.getResources().getString(R.string.apiKey) + "&startX=" + Double.toString(start_point.getLongitude())
                                + "&startY=" + Double.toString(start_point.getLatitude()) + "&endX=" + Double.toString(end_point.getLongitude()) +
                                "&endY=" + Double.toString(end_point.getLatitude()) + "&startName=start&endName=end" +
                                "&passList=" + passList + "&searchOption=" + searchOption);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    conn.setDoOutput(true);

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        reader.close();
                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start(); // Thread 실행
        try {
            mThread.join();

            JSONArray features = new JSONObject(receiveMsg).getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject jsonObject = features.getJSONObject(i);
                jsonObject = jsonObject.getJSONObject("geometry");
                if (jsonObject.getString("type").equals("LineString")) {
                    coordinateArray = (JSONArray) jsonObject.get("coordinates");
                    test += jsonObject.getString("coordinates") + ",";
                }

            }

            test = test.replace("[", "").replace("]", "");
            stringTokenizer = new StringTokenizer(test, ",");

            while (stringTokenizer.hasMoreTokens()) {
                if (i % 2 == 0) {
                    x2 = stringTokenizer.nextToken();
                    x1 = Double.parseDouble(x2);
                } else {
                    y2 = stringTokenizer.nextToken();
                    y1 = Double.parseDouble(y2);
                    TMapPoint point = new TMapPoint(y1, x1);
                        // 도로명으로 변경 array 저장
                    list.add(point);
                    coordinateID.add(getLinkID(y2, x2));
                    Thread.sleep(50);
                }
                i++;
            }

            Log.d("test", test);
            for (int j = 0; j < coordinateID.size(); j++) {
                Log.d("경로 id 목록", coordinateID.get(j));
            }

        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean checkRoute(){
        for(String str : postID){
            /*for(int j = 0; j < coordinateID.size(); j++){
                test++;
                Log.d("test", postID.get(i) + coordinateID.get(j));
                if(postID.get(i).equals(coordinateID.get(j)))
                    return false;
            }*/
            if(coordinateID.contains(str)) {
                coordinateID.clear();
                return false;
            }
        }
        coordinateID.clear();
        return true;
    }
    public String getPassList() {
        return "";
    }
}


