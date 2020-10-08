package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.example.mobilitySupport.map.MapFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostServerRequest extends AppCompatActivity {
    private String baseUrl = "http://121.168.1.81/";    // 기본 주소에 추가하여 사용
    //private String baseUrl = "http://192.168.81.240/";    // 기본 주소에 추가하여 사용

    private Map<String, String> parameters;
    private Context context;

    // 해당 프래그먼트의 context 보냄(getContext() 이용)
    public PostServerRequest(Context context){
        this.context = context;
    }

    public void modifyPostPlace(String postNum, String userID, String latitude, String longitude, String availability, String type,
                                String elevator, String wheel, View v, String url){ //제보글(길)
        parameters = new HashMap<>();
        parameters.put("postNum", postNum);
        parameters.put("userID", userID);
        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);
        parameters.put("availability", availability);
        parameters.put("type", type);
        parameters.put("elevator", elevator);
        parameters.put("wheel", wheel);

        sendRequest(v, url);

        /*
        postServerRequest postServerRequest = new postServerRequest(getContext());
        postServerRequest.modifyPostPlace(id, lat, lon, spinner_availability.getSelectedItem().toString(),
                            spinner_type.getSelectedItem().toString(), elevator.getText().toString(),
                            wheel.getText().toString(), v, "modifypostPlace.php");
         */
    }

    public void modifyPostRoad(String postNum, String userID, String latitude, String longitude, String availability, String type,
                               String stair, String roadBreakage, String slope, View v, String url) { //제보글(길)
        parameters = new HashMap<>();
        parameters.put("postNum", postNum);
        parameters.put("userID", userID);
        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);
        parameters.put("availability", availability);
        parameters.put("type", type);
        parameters.put("stair", stair);
        parameters.put("roadBreakage", roadBreakage);
        parameters.put("slope", slope);

        sendRequest(v, url);

        /*
        postServerRequest postServerRequest = new postServerRequest(getContext());
        postServerRequest.modifyPostRoad(id, lat, lon, spinner_availability.getSelectedItem().toString(),
                            spinner_type.getSelectedItem().toString(), stairs.getText().toString(),
                            breakage.getText().toString(), spinner_angle.getSelectedItem().toString(), v, "modifypostRoad.php");
         */
    }

    public void deleteMyPostPlace(String postNum, String userID, View v, String url){ //특정 유저의 제보글(장소)
        parameters = new HashMap<>();
        parameters.put("postNum", postNum);
        parameters.put("userID", userID);

        sendRequest(v, url);

        /*
        postServerRequest postServerRequest = new postServerRequest(getContext());
        postServerRequest.deleteMyPostPlace(id, v, "deletePostPlace.php");
         */
    }

    public void deleteMyPostRoad(String postNum, String userID, View v, String url){//특정 유저의 제보글(길)
        parameters = new HashMap<>();
        parameters.put("postNum", postNum);
        parameters.put("userID", userID);

        sendRequest(v, url);

        /*
        postServerRequest postServerRequest = new postServerRequest(getContext());
        postServerRequest.deleteMyPostRoad(id, v, "deletePostRoad.php");
         */
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

                                //장소 제보글 수정된 경우
                                if(requestUrl.equals("modifypostPlace.php")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("제보글 수정 완료!")
                                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //페이지 이동(프래그먼트 사용 필요)
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            })
                                            .create().show();
                                }

                                //도로 제보글 수정된 경우
                                if(requestUrl.equals("modifypostRoad.php")){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("제보글 수정 완료!")
                                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //페이지 이동(프래그먼트 사용 필요)
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
//                                                    Navigation.findNavController(v).navigate(R.id.action_modifyRoadPostFragment_to_fragment_map);
                                                }
                                            })
                                            .create().show();
                                }

                                //장소 제보글 삭제
                                if(requestUrl.equals("deletePostPlace.php")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("제보글 삭제 완료!")
                                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //페이지 이동
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            });
                                    builder.create().show();
                                }

                                //도로 제보글 삭제
                                if(requestUrl.equals("deletePostRoad.php")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("제보글 삭제 완료!")
                                            .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //페이지 이동
                                                    //이부분이지금안됨...
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            });
                                    builder.create().show();

                                }
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