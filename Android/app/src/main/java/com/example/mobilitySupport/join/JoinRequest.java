package com.example.mobilitySupport.join;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {

    final static private String URL = "http://172.30.1.2/register.php";
    private Map<String, String> parameters;

    public JoinRequest(String userID, String userPassword, String userMail, String userType, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파마미터들을 전송함
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userMail", userMail);
        parameters.put("userType",userType);
        //parameters.put("userType",userType);
    }

    protected Map<String, String> getParams(){
        return parameters;
    }
}