package com.example.mobilitySupport.appInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.mobilitySupport.R;

import java.io.IOException;
import java.io.InputStream;

public class PersonalTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_terms_activity);

        TextView mTextView = (TextView) findViewById(R.id.textView9);

        try{
            InputStream in = getResources().openRawResource(R.raw.personalagreement);
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