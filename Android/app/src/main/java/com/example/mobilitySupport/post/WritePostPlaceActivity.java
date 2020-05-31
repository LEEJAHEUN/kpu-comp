package com.example.mobilitySupport.post;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.R;

public class WritePostPlaceActivity extends AppCompatActivity { // 제보글 작성 페이지(장소)

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writepost_road);
/*
        final String[] data = getResources().getStringArray(R.array.spinnerArray3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,data);
        Spinner spinner = (Spinner)findViewById(R.id.Availability);
        spinner.setAdapter(adapter);

        final String[] data2 = getResources().getStringArray(R.array.spinnerArray2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,data2);
        Spinner spinner2 = (Spinner) findViewById(R.id.stairs2);
        spinner2.setAdapter(adapter2);

        final String[] data3 = getResources().getStringArray(R.array.spinnerArray2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,data3);
        Spinner spinner3 = (Spinner)findViewById(R.id.elevator2);
        spinner3.setAdapter(adapter3);

        final String[] data4 = getResources().getStringArray(R.array.spinnerArray2);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,data4);
        Spinner spinner4 = (Spinner)findViewById(R.id.wheelchairSlope2);
        spinner4.setAdapter(adapter4);


 */
        imageview = (ImageView)findViewById(R.id.imagechoose);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    public void onClick(View v) { // 제보글 작성완료
        Button btn = (Button)findViewById(R.id.writeFin);
        btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        //Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                        //startActivity(intent);
                    }
                }
        );
    }

    public void onClick2(View v) { // 제보글 작성
        Button btn = (Button)findViewById(R.id.reset);
    }

    public void onClick3(View v) { // 돌아가기
        ImageButton btn = (ImageButton)findViewById(R.id.backButton);
        btn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        //Intent intent = new Intent(getApplicationContext(), Main7Activity.class);
                        //startActivity(intent);
                    }
                }
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }
}
