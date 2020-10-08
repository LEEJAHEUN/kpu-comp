package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilitySupport.R;
import com.example.mobilitySupport.post.MyPostServerRequest;


import static android.content.Context.MODE_PRIVATE;

//도로 제보글 목록 프래그먼트
public class RoadListFragment extends ListFragment {
    RoadListViewAdapter adapter;

    private String[] roadList = null;
    private String[] selectPost = new String[9];
    private String marker = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //어댑터 생성, 지정
        adapter = new RoadListViewAdapter();
        setListAdapter(adapter);

        //번들 값을 받아옴
        Bundle bundle =getArguments();
        if(bundle != null){
            roadList = bundle.getStringArray("roadList");
            //배열에 저장된 값을 리스트에 추가
            for(int i=0;i<roadList.length;){
                adapter.addItem(roadList[i],roadList[i+1],roadList[i+2],roadList[i+3],roadList[i+4],
                        roadList[i+5],roadList[i+6],roadList[i+7],roadList[i+8]);
                i+=9;
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //리스트의 아이템 클릭 시 발생하는 이벤트
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        //리스트뷰의 텍스트를 받아옴

        RoadListItem item = (RoadListItem)l.getItemAtPosition(position);

        String postNumData = item.getPostNum();
        String writerData = item.getWriterID();
        String availabilityData = item.getAvailability();
        String typeData = item.getType();
        String angleData = item.getAngle();
        String stairsData = item.getStairs();
        String breakageData =item.getBreakage();
        String latitudeData = item.getLatitude();
        String longitudeData = item.getLongitude();

/*        selectPost[0]=availabilityData;
        selectPost[1]=typeData;
        selectPost[2]=angleData;
        selectPost[3]=stairsData;
        selectPost[4]=breakageData;*/

        selectPost[0]=postNumData;
        selectPost[1]=writerData;
        selectPost[2]=availabilityData;
        selectPost[3]=typeData;
        selectPost[4]=angleData;
        selectPost[5]=stairsData;
        selectPost[6]=breakageData;
        selectPost[7]=latitudeData;
        selectPost[8]=longitudeData;

//        super.onListItemClick(l, v, position, id);

        //화면 이동(인텐트)
        Intent intent = new Intent(getActivity(),CheckRoadPostActivity.class);
        intent.putExtra("selectPost",selectPost);

/*        Intent intent = new Intent(getActivity(),CheckRoadPostActivity.class);
        intent.putExtra("selectPost",selectPost);
        intent.putExtra("selectPostNum",postNumData);
        intent.putExtra("selectLatitude",latitudeData);
        intent.putExtra("selectLongitude",longitudeData);*/

        startActivity(intent);

/*        //번들 이용해 선택한 제보글 내용 전송
        CheckRoadPostFragment checkRoadPostFragment = new CheckRoadPostFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("selectPost",selectPost);
        bundle.putString("selectPostNum",postNumData);
        bundle.putString("selectLatitude",latitudeData);
        bundle.putString("selectLongitude",longitudeData);
        System.out.println("선택해서 보낼 item 번들:"+bundle);
        checkRoadPostFragment.setArguments(bundle);*/

        //선택한 뷰는 does not have a NavController set
//        Navigation.findNavController(v).navigate(R.id.action_roadListFragment_to_checkRoadPostFragment);
    }

/*    //(프래그먼트 외부에서)아이템 추가
    public  void  addItem(String availability, String type, String angle, String stairs, String breakage){
        adapter.addItem(availability,type,angle,stairs,breakage);
    }*/
}
