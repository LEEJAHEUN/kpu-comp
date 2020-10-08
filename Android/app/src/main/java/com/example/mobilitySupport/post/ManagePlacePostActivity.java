package com.example.mobilitySupport.post;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilitySupport.R;

//장소 제보글 목록 액티비티
public class ManagePlacePostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //리퀘스트에서 전송한 값을 받음
        Intent intent = getIntent();
        String[] placeList= intent.getStringArrayExtra("placeList");
//        String marker = intent.getStringExtra("marker");
//        System.out.println("ManagePlacePost액티비티에서 받은 인텐드: "+placeList+","+marker);

        for(int i=0;i<placeList.length;i++){
            System.out.println("ManagePlacePost액티비티에서 받은 인텐트: "+placeList[i]);
        }

        //목록 프래그먼트에 데이터 전송, 화면 이동
        PlaceListFragment placeListFragment = new PlaceListFragment();

        //전달할 값을 번들에 저장
        Bundle bundle = new Bundle();
        bundle.putStringArray("placeList",placeList);
/*        if(marker != null){
            //마커를 클릭해서 온 경우
            bundle.putString("marker",marker);
        }*/
        placeListFragment.setArguments(bundle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.managepost_place_activity);

        getSupportFragmentManager().beginTransaction().replace(R.id.placelistfragment ,placeListFragment).commit();
    }
}
