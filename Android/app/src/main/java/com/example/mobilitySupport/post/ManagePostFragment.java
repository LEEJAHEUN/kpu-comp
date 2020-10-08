package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


//제보글 관리 프래그먼트
public class ManagePostFragment extends Fragment {

    MainActivity activity = null;

    private SharedPreferences appData;
    String id = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 레이아웃은 제보글 작성시 길/도로 선택과 동일
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_choose_posttype, container, false);

        SearchView searchView = activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Button buttonRoad = view.findViewById(R.id.writeRoad);
        Button buttonPlace = view.findViewById(R.id.wrtiePlace);

        appData = getActivity().getSharedPreferences("appData", MODE_PRIVATE);
        id = appData.getString("ID", "");   // 로그인 정보

        //메인 액티비티에서 마커 클릭 후 이동시 번들값을 가져옴
        double markerLat = 0;
        double markerLon = 0;
        final Bundle bundle = getArguments();
        System.out.println("마커 클릭 후 전달받은 위치:"+bundle);
        if(bundle != null){
            markerLat = bundle.getDouble("markerLat");
            markerLon = bundle.getDouble("markerLon");
        }
        final double finalMarkerLat = markerLat;
        final double finalMarkerLon = markerLon;

        //도로 버튼 클릭시 도로 제보글 액티비티로 이동
        buttonRoad.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버와 연결(자신의 도로 제보글 목록으로 이동)
                MyPostServerRequest myPostServerRequest = new MyPostServerRequest(getContext());
                myPostServerRequest.getMyPostRoad(id,container,"readMyPostRoad.php");
/*                //마이페이지에서 접근한 경우
                if(bundle == null){
                    //서버와 연결(자신의 도로 제보글 목록으로 이동)
                    MyPostServerRequest myPostServerRequest = new MyPostServerRequest(getContext());
                    myPostServerRequest.getMyPostRoad(id,container,"readMyPostRoad.php");
                }
                //메인 액티비티에서 마커를 클릭해 온 경우
                else{
                    //서버와 연결(해당 좌표의 제보글 목록으로 이동)
//                    System.out.println("마커 클릭해서 리퀘스트로 이동함!");
                    MyPostServerRequest myPostServerRequest = new MyPostServerRequest(getContext());
                    myPostServerRequest.getPostRoad(Double.toString(finalMarkerLat), Double.toString(finalMarkerLon),container,"readPostRoad.php");
                }*/
            }
        });


        //장소 버튼 클릭시 장소 제보글 액티비티로 이동
        buttonPlace.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버와 연결(자신의 장소 제보글 목록으로 이동)
                MyPostServerRequest myPostServerRequest = new MyPostServerRequest(getContext());
                myPostServerRequest.getMyPostPlace(id,container,"readMyPostPlace.php");
/*                //마이페이지에서 접근한 경우
                if (bundle == null) {
                    //서버와 연결(자신의 장소 제보글 목록으로 이동)
                    MyPostServerRequest myPostServerRequest = new MyPostServerRequest(getContext());
                    myPostServerRequest.getMyPostPlace(id,container,"readMyPostPlace.php");
                }
                //메인 액티비티에서 마커를 클릭해 온 경우
                else{
                    //서버와 연결(해당 좌표의 제보글 목록으로 이동)
//                    System.out.println("마커 클릭해서 리퀘스트로 이동함!");
                    MyPostServerRequest myPostServerRequest = new MyPostServerRequest(getContext());
                    myPostServerRequest.getPostPlace(Double.toString(finalMarkerLat), Double.toString(finalMarkerLon),container,"readPostPlace.php");
                }*/

            }
        });
        return view;
    }
}

