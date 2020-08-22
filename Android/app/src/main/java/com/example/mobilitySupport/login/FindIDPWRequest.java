package com.example.mobilitySupport.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FindIDPWRequest extends StringRequest{
    private Map<String, String> parameters;

    public FindIDPWRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
    }


    public void findID(String email){
        parameters = new HashMap<>();
        parameters.put("userMail", email);
    }

    public void findPW(String id){
        parameters = new HashMap<>();
        parameters.put("userID", id);
    }

    protected Map<String, String> getParams() { return parameters;}

}
