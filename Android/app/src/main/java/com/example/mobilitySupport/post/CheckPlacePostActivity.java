package com.example.mobilitySupport.post;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.R;

//장소 제보글 확인 액티비티
public class CheckPlacePostActivity extends AppCompatActivity {
    TextView availability,type,elevator,wheel;
    Button modify, delete;
//    String latitude, longitude;

    private SharedPreferences appData;
    String id = null;   // 받아올 사용자 아이디

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        id = appData.getString("ID", "");   // 로그인 정보

        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkpost_place);

        availability = (TextView)findViewById(R.id.availability_check);
        type = (TextView)findViewById(R.id.type_check);
        elevator = (TextView)findViewById(R.id.elevator_check);
        wheel = (TextView)findViewById(R.id.wheel_check);

        modify = (Button)findViewById(R.id.modify_check);
        delete = (Button)findViewById(R.id.delete_check);

        //선택한 제보글 내용을 받아옴
        Intent intent = getIntent();
        final String[] selectPost= intent.getStringArrayExtra("selectPost");
/*        final String postNum = intent.getStringExtra("selectPostNum");
        latitude= intent.getStringExtra("selectLatitude");
        longitude = intent.getStringExtra("selectLongitude");*/

        //다른 사람이 작성한 제보글은 수정, 삭제 불가(버튼 없앰)
        if(!(id.equals(selectPost[1]))){
            modify.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        availability.setText(selectPost[2]);
        type.setText(selectPost[3]);
        elevator.setText(selectPost[4]);
        wheel.setText(selectPost[5]);

        //수정 버튼 클릭시 도로 제보글 수정화면으로 이동
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //수정 프래그먼트로 이동
            ModifyPlacePostFragment modifyPlacePostFragment = new ModifyPlacePostFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray("selectPost",selectPost);
/*            bundle.putString("selectPostNum",postNum);
            bundle.putString("selectLatitude",latitude);
            bundle.putString("selectLongitude",longitude);*/
            modifyPlacePostFragment.setArguments(bundle);

            setContentView(R.layout.fragment_modifypost_place);
            getSupportFragmentManager().beginTransaction().replace(R.id.modifyPlaceFragment,modifyPlacePostFragment).commit();

            }
        });

        //삭제 버튼 클릭시 삭제여부를 물어봄
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckPlacePostActivity.this);
                builder.setMessage("제보글을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //서버랑 연동함
                        PostServerRequest postServerRequest = new PostServerRequest(CheckPlacePostActivity.this);
                        postServerRequest.deleteMyPostPlace(selectPost[0],id, v,"deletePostPlace.php");
                    }
                });
                builder.setNegativeButton("아니오",null);
                builder.create().show();

            }
        });
    }
}
