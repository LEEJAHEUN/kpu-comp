/*
로그인 구현 클래스

로그인 구현 필요 (서버 연동, id와 비밀번호 확인, 존재하지 않는 id)
특수문자나 글자 수 이상 입력시 안내 문자
아이디 비밀번호 찾기 필요
회원 가입 버튼 완료

 */

package com.example.mobilitySupport.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.example.mobilitySupport.join.JoinActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class LoginActivity<item> extends AppCompatActivity {
    TextInputLayout inputLayoutID = null;  // id 입력창
    TextInputLayout inputLayoutPW = null; // pw 입력창

    private SharedPreferences appData;

    String userID = null;
    String userPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        // 비밀번호 표시 온 오프 설정
        inputLayoutPW = (TextInputLayout)findViewById(R.id.textInputLayoutPW);
        inputLayoutPW.setPasswordVisibilityToggleEnabled(true);
    }

    public void login(final View v) {
        // 입력창 에러 관리
        manageInputError();

        EditText idText = (EditText) findViewById(R.id.insert_ID);
        EditText pwText = (EditText) findViewById(R.id.insert_PW);

        userID = idText.getText().toString();
        userPassword = pwText.getText().toString();

        Response.Listener<String>  responseListener = new Response.Listener<String> (){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        String id = jsonResponse.getString("userID");
                        save(id);
                        nonMember(v);
                        finish();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("로그인 실패")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }

    public void signUp(View v) {
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
    }

    public void nonMember(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    // 아이디 비번 찾기 버튼 클릭 시
    public void findIDPW(View v) {

    }

    // 입력창 에러 관리
    public void manageInputError(){
        inputLayoutID = (TextInputLayout)findViewById(R.id.textInputLayoutID);

        EditText idEditText = inputLayoutID.getEditText();
        EditText pwEditText = inputLayoutPW.getEditText();

        if(idEditText.getText().toString().isEmpty()){
            inputLayoutID.setError("아이디를 입력해주십시오");
        }
        else { inputLayoutID.setError(null); }

        if(pwEditText.getText().toString().isEmpty()){
            inputLayoutPW.setError("비밀번호를 입력해주십시오");
        }
        else { inputLayoutPW.setError(null); }
    }

    private void save(String userID){
        SharedPreferences.Editor editor = appData.edit();
        editor.putString("ID", userID);
        editor.putBoolean("SAVE_LOGIN_DATA", true);
        editor.apply(); editor.commit();
    }
}
