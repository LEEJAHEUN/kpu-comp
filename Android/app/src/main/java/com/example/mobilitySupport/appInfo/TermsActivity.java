package com.example.mobilitySupport.appInfo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.R;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class TermsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_activity);

        TextView mTextView = (TextView) findViewById(R.id.textView8);

        try{
            InputStream in = getResources().openRawResource(R.raw.agreement);
            byte[] b = new byte[in.available()];
            in.read(b);
            String s = new String(b);

            mTextView.append(s);
            mTextView.setMovementMethod(new ScrollingMovementMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}