/*
로그인 구현 클래스

로그인 구현 필요 (서버 연동, id와 비밀번호 확인, 존재하지 않는 id)
특수문자나 글자 수 이상 입력시 안내 문자
아이디 비밀번호 찾기 필요
회원 가입 버튼 완료

 */

package com.example.mobilitySupport.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.join.JoinActivity;
import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity<item> extends AppCompatActivity {
    TextInputLayout inputLayoutID = null;  // id 입력창
    TextInputLayout inputLayoutPW = null; // pw 입력창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // 비밀번호 표시 온 오프 설정
        inputLayoutPW = (TextInputLayout)findViewById(R.id.textInputLayoutPW);
        inputLayoutPW.setPasswordVisibilityToggleEnabled(true);
    }

    // 로그인 버튼 클릭 시
    // 아직 구현 전
    public void login(View v) {
        // 입력창 에러 관리
        manageInputError();
        //new LoginJSONTask().execute("http://127.0.0.1:3000/");
        // 서버 연결 코드

        /*
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
         */
    }

    // 회원가입 버튼 클릭 시
    public void signUp(View v) {
        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
    }

    // 비회원 이용 버튼 클릭 시
    public void nonMember(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    // 아이디 비번 찾기 버튼 클릭 시
    //  화면 구현 필요
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
}
