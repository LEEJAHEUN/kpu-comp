package com.example.mobilitySupport.post;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

//장소 제보글 목록 프래그먼트
public class PlaceListFragment extends ListFragment {
    PlaceListViewAdapter adapter;

    private String[] placeList = null;
    private String[] selectPost = new String[8];
    private String marker = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //어댑터 생성, 지정
        adapter = new PlaceListViewAdapter();
        setListAdapter(adapter);

        //번들 값을 받아옴
        Bundle bundle = getArguments();
        if(bundle != null){
            placeList = bundle.getStringArray("placeList");
            //배열에 저장된 값을 리스트에 추가
            for(int i=0;i<placeList.length;){
                adapter.addItem(placeList[i],placeList[i+1], placeList[i+2],placeList[i+3],
                        placeList[i+4],placeList[i+5],placeList[i+6],placeList[i+7]);
                i+=8;
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //리스트의 아이템 클릭 시 발생하는 이벤트
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {

        PlaceListItem item = (PlaceListItem)l.getItemAtPosition(position);

        //선택한 item의 정보를 받아옴
        String postNumData = item.getPostNum();
        String writerData = item.getWriterID();
        String availabilityData = item.getAvailability();
        String typeData = item.getType();
        String elevatorData = item.getElevator();
        String wheelData = item.getWheel();
        String latitudeData = item.getLatitude();
        String longitudeData = item.getLongitude();

        selectPost[0]=postNumData;
        selectPost[1]=writerData;
        selectPost[2]=availabilityData;
        selectPost[3]=typeData;
        selectPost[4]=elevatorData;
        selectPost[5]=wheelData;
        selectPost[6]=latitudeData;
        selectPost[7]=longitudeData;

//        super.onListItemClick(l, v, position, id);

        //선택한 item의 정보를 intent를 사용해 확인 액티비티로 전달
        Intent intent = new Intent(getActivity(),CheckPlacePostActivity.class);
        intent.putExtra("selectPost",selectPost);
/*        intent.putExtra("selectPostNum",postNumData);
        intent.putExtra("selectLatitude",latitudeData);
        intent.putExtra("selectLongitude",longitudeData);*/

        startActivity(intent);
    }

/*    //(프래그먼트 외부에서)아이템 추가
    public  void  addItem(String availability, String type, String elevator, String wheel){
        adapter.addItem(availability,type,elevator,wheel);
    }*/
}
