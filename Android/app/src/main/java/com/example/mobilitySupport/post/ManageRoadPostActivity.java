package com.example.mobilitySupport.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.post.RoadListFragment;
import com.example.mobilitySupport.post.MyPostServerRequest;

import com.example.mobilitySupport.R;

//도로 제보글 목록 액티비티
public class ManageRoadPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //리퀘스트에서 전송한 값을 받음
        Intent intent = getIntent();
        String[] roadList= intent.getStringArrayExtra("roadList");
//        String marker = intent.getStringExtra("marker");
//        System.out.println("ManageRoadPost액티비티에서 받은 인텐드: "+roadList+","+marker);
        for(int i=0;i<roadList.length;i++){
            System.out.println("ManageRoadPost액티비티에서 받은 인텐트: "+roadList[i]);
        }
        //목록 프래그먼트에 데이터 전송, 화면 이동
        RoadListFragment roadListFragment = new RoadListFragment();

        //전달할 값을 번들에 저장
        Bundle bundle = new Bundle();
        bundle.putStringArray("roadList",roadList);
/*        if(marker != null){
            //마커를 클릭해서 온 경우
            bundle.putString("marker",marker);
        }*/
        roadListFragment.setArguments(bundle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.managepost_road_activity);

        getSupportFragmentManager().beginTransaction().replace(R.id.roadlistfragment ,roadListFragment).commit();
    }
}

