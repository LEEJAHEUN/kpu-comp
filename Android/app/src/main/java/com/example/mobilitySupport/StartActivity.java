/*
 로고화면 출력 클래스
 */

package com.example.mobilitySupport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.login.LoginActivity;

public class StartActivity<item> extends AppCompatActivity {

    int time = 3000;    // 대기 시간
    private SharedPreferences appData;
    Boolean loginData;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        loginData = appData.getBoolean("SAVE_LOGIN_DATA", false);

        // time 시간 동안 로고 화면 출력
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(loginData)   // 자동 로그인
                    intent = new Intent(StartActivity.this, MainActivity.class);
                else
                    intent = new Intent(StartActivity.this, LoginActivity.class);

                startActivity(intent);
                finish();
            }
        }, time);

    }
}
