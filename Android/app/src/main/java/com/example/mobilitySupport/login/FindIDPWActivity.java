package com.example.mobilitySupport.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.Map;

public class FindIDPWActivity extends AppCompatActivity {
    TextInputLayout emailLayout;
    TextInputLayout idLayout;

    TextInputEditText findByEmail;
    TextInputEditText findByID;

    private String baseUrl = "http://121.168.1.81/";    // 기본 주소에 추가하여 사용
    String type = null;
    Response.Listener<String>  responseListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id_pw_activity);

        emailLayout = findViewById(R.id.TextInputLayout_findID);
        idLayout = findViewById(R.id.TextInputLayout_findPW);
        findByEmail = findViewById(R.id.findByEmail);
        findByID = findViewById(R.id.findByID);

        responseListener = new Response.Listener<String> (){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(FindIDPWActivity.this);
                        builder.setMessage("찾으시는 " + type + "는 '" + jsonResponse.getString("result") + "' 입니다.")
                                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    }
                                })
                                .create().show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }

    public void findID(View v){
        if(manageInputError(emailLayout, findByEmail)) {
            type = "아이디";
            FindIDPWRequest request = new FindIDPWRequest(baseUrl + "findId.php", responseListener, null);
            request.findID(findByEmail.getText().toString());
            sendRequest(request);
        }
    }

    public void findPW(View v){
        if(manageInputError(idLayout, findByID)){
            type = "비밀번호";
            FindIDPWRequest request = new FindIDPWRequest(baseUrl + "findPw.php", responseListener, null);
            request.findPW(findByID.getText().toString());
            sendRequest(request);
        }
    }

    public boolean manageInputError(TextInputLayout layout, TextInputEditText editText){
        if (editText.getText().toString().isEmpty()) {
            layout.setError("입력 후 버튼을 눌러주세요");
            return false;
        }
        return true;
    }

    public void sendRequest(FindIDPWRequest request){
        request.setShouldCache(false);
        Volley.newRequestQueue(FindIDPWActivity.this).add(request);
    }
}
