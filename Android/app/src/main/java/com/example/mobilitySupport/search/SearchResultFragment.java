package com.example.mobilitySupport.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzhl.runOnUiThread;

public class SearchResultFragment extends Fragment {
    MainActivity activity = null;
    SearchRecyclerAdapter adapter;
    String type = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_search_result, container, false);

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        type = SearchResultFragmentArgs.fromBundle(getArguments()).getType();    // 출발지/도착지 구분
        String searchWorld = SearchResultFragmentArgs.fromBundle(getArguments()).getSearchWord();   // 검색어

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SearchRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        toolbar.setTitle(searchWorld);
        createSearchResultList(searchWorld);

        return view;
    }

    // 검색어 이용 리스트 생성
    public void createSearchResultList(String searchWorld) {

        TMapData tMapData = new TMapData();
        TMapPoint point = activity.getLocation();   // 현재 위치 받아오기

        // 33km 내, 100개 결과 출력
        tMapData.findAroundKeywordPOI(point, searchWorld, 33, 100, new TMapData.FindAroundKeywordPOIListenerCallback() {
            @Override
            public void onFindAroundKeywordPOI(ArrayList<TMapPOIItem> arrayList) {
                adapter.addItem(arrayList, activity, type);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
