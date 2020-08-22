package com.example.mobilitySupport.member;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MemberInfoGetRequest extends StringRequest {
    final static private String URL = "http://121.168.1.81/memberInfoGet.php";
    private Map<String, String> parameters;

    public MemberInfoGetRequest(String userID, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파라미터들을 전송함
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    protected Map<String, String> getParams(){
        return parameters;
    }
}