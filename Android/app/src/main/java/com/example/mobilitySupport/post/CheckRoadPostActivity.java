package com.example.mobilitySupport.post;

import android.content.Context;
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
import androidx.navigation.Navigation;

import com.example.mobilitySupport.R;

//도로 제보글 확인 액티비티
public class CheckRoadPostActivity extends AppCompatActivity {
    TextView availability,type,angle,stairs,breakage;
    Button modify, delete;
//    String latitude, longitude;

    private SharedPreferences appData;
    String id = null;   // 받아올 사용자 아이디

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        id = appData.getString("ID", "");   // 로그인 정보

        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkpost_road);

        availability = (TextView)findViewById(R.id.availability_check);
        type = (TextView)findViewById(R.id.type_check);
        angle = (TextView)findViewById(R.id.angle_check);
        stairs = (TextView)findViewById(R.id.stairs_check);
        breakage = (TextView)findViewById(R.id.breakage_check);

        modify = (Button)findViewById(R.id.modify_check_road);
        delete = (Button)findViewById(R.id.delete_check_road);

        Intent intent = getIntent();
        final String[] selectPost= intent.getStringArrayExtra("selectPost");

        //다른 사람이 작성한 제보글은 수정, 삭제 불가(버튼 없앰)
        if(!(id.equals(selectPost[1]))){
            modify.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        /*        final String postNum = intent.getStringExtra("selectPostNum");
        latitude= intent.getStringExtra("selectLatitude");
        longitude = intent.getStringExtra("selectLongitude");*/

/*        availability.setText(selectPost[0]);
        type.setText(selectPost[1]);
        angle.setText(selectPost[2]);
        stairs.setText(selectPost[3]);
        breakage.setText(selectPost[4]);*/

        availability.setText(selectPost[2]);
        type.setText(selectPost[3]);
        angle.setText(selectPost[4]);
        stairs.setText(selectPost[5]);
        breakage.setText(selectPost[6]);

        //수정 버튼 클릭시 도로 제보글 수정화면으로 이동
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //수정 프래그먼트로 이동
            ModifyRoadPostFragment modifyRoadPostFragment = new ModifyRoadPostFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray("selectPost",selectPost);
/*            bundle.putString("selectPostNum",postNum);
            bundle.putString("selectLatitude",latitude);
            bundle.putString("selectLongitude",longitude);*/
            modifyRoadPostFragment.setArguments(bundle);

            setContentView(R.layout.fragment_modifypost_road);
            getSupportFragmentManager().beginTransaction().replace(R.id.modifyRoadFragment,modifyRoadPostFragment).commit();

            }
        });

        //삭제 버튼 클릭시 삭제여부를 물어봄
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(CheckRoadPostActivity.this);
               builder.setMessage("제보글을 삭제하시겠습니까?");
               builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //서버랑 연동함
                       PostServerRequest postServerRequest = new PostServerRequest(CheckRoadPostActivity.this);
                       postServerRequest.deleteMyPostRoad(selectPost[0],id, v,"deletePostRoad.php");
                   }
               });
               builder.setNegativeButton("아니오",null);
               builder.create().show();

            }
        });
    }
}
