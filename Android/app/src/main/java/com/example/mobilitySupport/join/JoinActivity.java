package com.example.mobilitySupport.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mobilitySupport.R;
import com.example.mobilitySupport.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    Spinner spinner = null;                // chooseType 스피너
    ArrayAdapter<String> adapter = null;

    TextInputLayout id = null;
    TextInputLayout pw = null;
    TextInputLayout pwCheck = null;
    TextInputLayout email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        // spinner 초기화
        final String[] data = getResources().getStringArray(R.array.spinnerArray_type);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        spinner = (Spinner) findViewById(R.id.chooseType);
        spinner.setAdapter(adapter);

        id = (TextInputLayout)findViewById(R.id.TextInputLayout_join_id);
        email = (TextInputLayout)findViewById(R.id.TextInputLayout_email);

        // 비밀번호 표시 온 오프 설정
        pw = (TextInputLayout) findViewById(R.id.TextInputLayout_join_PW);
        pwCheck = (TextInputLayout) findViewById(R.id.TextInputLayer_PWCheck);
        pw.setPasswordVisibilityToggleEnabled(true);
        pwCheck.setPasswordVisibilityToggleEnabled(true);
    }

    // 작성완료
    public void writeFin(View v) {
        textManage();

        String userID = id.getEditText().getText().toString();  //final ?
        String userPassword = pw.getEditText().getText().toString();
        String userMail = email.getEditText().getText().toString();
        String userType = spinner.getSelectedItem().toString();

        Response.Listener<String> responseListener = new Response.Listener<String> (){
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                        builder.setMessage("회원가입 완료")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();

                        // 확인 클릭 시 이동할 수 있게
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        JoinActivity.this.startActivity(intent);
                        finish();
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                        builder.setMessage("회원가입 실패 : 입력한 정보를 다시 확인해주십시오.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        JoinRequest JoinRequest = new JoinRequest(userID, userPassword, userMail, userType, responseListener);
        RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
        queue.add(JoinRequest);
    }


    // 입력 초기화
    public void reset(View v) {
        id.getEditText().setText("");
        pw.getEditText().setText("");
        email.getEditText().setText("");
        spinner.setAdapter(adapter);
        pwCheck.getEditText().setText("");
    }

    public void textManage(){
        String idText = id.getEditText().getText().toString();
        String pwText = pw.getEditText().getText().toString();
        String pwCheckText = pwCheck.getEditText().getText().toString();
        String emailText = email.getEditText().getText().toString();

        if(idText.isEmpty())
            id.setError("아이디를 입력해주십시오");
        else { id.setError(null); }

        if(pwText.isEmpty())
            pw.setError("비밀번호를 입력해주십시오");
        else { pw.setError(null); }

        if(pwCheckText.isEmpty())
            pwCheck.setError("비밀번호를 확인해주십시오");
       else if(!(pwCheckText.equals(pwText)))
            pwCheck.setError("비밀번호와 다릅니다");
        else { pwCheck.setError(null);}

        if(emailText.isEmpty())
            email.setError("이메일 주소를 입력해주십시오");
        else { email.setError(null);}

    }
}


