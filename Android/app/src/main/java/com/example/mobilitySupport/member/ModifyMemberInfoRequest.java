package com.example.mobilitySupport.member;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyMemberInfoRequest extends StringRequest {

    final static private String URL = "http://121.168.1.81/modifyMemberInfo.php";
    //final static private String URL = "http://192.168.81.240/modifyMemberInfo.php";    // 기본 주소에 추가하여 사용
    private Map<String, String> parameters;

    public ModifyMemberInfoRequest(String userID, String userPW, String userMail, String userType, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파라미터들을 전송함
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPW", userPW);
        parameters.put("userMail", userMail);
        parameters.put("userType",userType);
    }

    protected Map<String, String> getParams(){
        return parameters;
    }
}