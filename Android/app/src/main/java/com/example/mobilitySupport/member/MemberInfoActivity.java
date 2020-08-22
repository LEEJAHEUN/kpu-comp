package com.example.mobilitySupport.member;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.R;
import com.example.mobilitySupport.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MemberInfoActivity extends AppCompatActivity {
    Spinner spinner = null;                // chooseType 스피너
    ArrayAdapter<String> adapter = null;
    private SharedPreferences appData;

    EditText userID = null;
    EditText userPW = null;
    TextInputLayout pw = null;
    TextInputLayout pwCheck = null;
    EditText email = null;
    String id = null;

    Boolean idCheck = true;
    Boolean passwdCheck = true;
    Boolean checkCheck = true;
    Boolean mailCheck = true;

    //수정완료 버튼
    Button writeFin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        id = appData.getString("ID", "");   // 로그인 정보

        // spinner 초기화
        final String[] data = getResources().getStringArray(R.array.spinnerArray_type);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        spinner = (Spinner) findViewById(R.id.chooseType);
        spinner.setAdapter(adapter);

        userID = (EditText) findViewById(R.id.register_ID);
        userPW = (EditText) findViewById(R.id.register_PW);
        email = (EditText)findViewById(R.id.register_email);

        //버튼 텍스트 변경
        writeFin = (Button)findViewById(R.id.writeFin);
        writeFin.setText("수정 완료");

        // 비밀번호 표시 온 오프 설정
        pw = (TextInputLayout)findViewById(R.id.TextInputLayout_join_PW);
        pwCheck = (TextInputLayout) findViewById(R.id.TextInputLayer_PWCheck);
        pw.setPasswordVisibilityToggleEnabled(true);
        pwCheck.setPasswordVisibilityToggleEnabled(true);

        //비밀번호 edittext
        userPW = (EditText)findViewById(R.id.register_PW);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        String idid = jsonResponse.getString("userID");
                        String pw = jsonResponse.getString("userPW");
                        String mail = jsonResponse.getString("userMail");
                        String type = jsonResponse.getString("userType");

                        userID.append(idid);
                        userPW.append(pw);
                        email.append(mail);

                        if(type.equals("임산부")){
                            spinner.setSelection(1);
                        }
                        if(type.equals("휠체어 이용자")){
                            spinner.setSelection(2);
                        }
                        if(type.equals("보조기구 이용자")){
                            spinner.setSelection(3);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        MemberInfoGetRequest MemberInfoGetRequest = new MemberInfoGetRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MemberInfoActivity.this);
        queue.add(MemberInfoGetRequest);
    }

    // 입력 초기화
    public void reset(View v) {
//        userID.setText("");
        pw.getEditText().setText("");
        email.setText("");
        spinner.setAdapter(adapter);
        pwCheck.getEditText().setText("");
    }

    //수정완료
    public void writeFin(View v) {
        textManage();
        if(!idCheck || !passwdCheck || !checkCheck || !mailCheck ||
                spinner.getSelectedItem().toString().equals("선택"))
            return;

        String id = userID.getText().toString();
        String userPassword = userPW.getText().toString();
        String userMail = email.getText().toString();
        String userType = spinner.getSelectedItem().toString();

        Response.Listener<String> responseListener = new Response.Listener<String> (){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MemberInfoActivity.this);
                        builder.setMessage("회원 정보 수정이 완료되었습니다!")
                                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MemberInfoActivity.this, LoginActivity.class);
                                        MemberInfoActivity.this.startActivity(intent);
                                        finish();
                                    }
                                })
                                .create()
                                .show();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MemberInfoActivity.this);
                        builder.setMessage("수정 실패 : 입력한 정보를 다시 확인해주십시오.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        ModifyMemberInfoRequest modifyMemberInfo = new ModifyMemberInfoRequest(id, userPassword, userMail, userType, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MemberInfoActivity.this);
        queue.add(modifyMemberInfo);
    }

    //수정조건
    public void textManage(){
//        String idText = id.getEditText().getText().toString();
        String pwText = pw.getEditText().getText().toString();
        String pwCheckText = pwCheck.getEditText().getText().toString();
        String emailText = email.getText().toString();

/*        if(idText.isEmpty()) {
            idCheck = false; id.setError("아이디를 입력해주십시오");
        }
        else {
            idCheck = true; id.setError(null);
        }*/

        if(pwText.isEmpty()) {
            passwdCheck = false; pw.setError("비밀번호를 입력해주십시오");
        }
        else {
            passwdCheck = true; pw.setError(null);
        }

        if(pwCheckText.isEmpty()) {
            checkCheck = false; pwCheck.setError("비밀번호를 확인해주십시오");
        }
        else if(!(pwCheckText.equals(pwText))) {
            checkCheck = false; pwCheck.setError("비밀번호와 다릅니다");
        }
        else {
            checkCheck = true; pwCheck.setError(null);
        }

        if(emailText.isEmpty()) {
            mailCheck = false; email.setError("이메일 주소를 입력해주십시오");
        }
        else { mailCheck = true; email.setError(null);}

    }

    protected String uniToKsc(String uni) throws UnsupportedEncodingException{
        return new String(uni.getBytes("8859_1"),"KSC5601");
    }
}