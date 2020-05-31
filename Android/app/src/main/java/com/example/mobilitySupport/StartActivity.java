/*
 로고화면 출력 클래스
 */

package com.example.mobilitySupport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.login.LoginActivity;

public class StartActivity<item> extends AppCompatActivity {

    // 대기 시간
    int time = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        // time 시간 동안 로고 화면 출력
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);
    }
}
