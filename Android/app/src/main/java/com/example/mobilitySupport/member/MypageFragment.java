package com.example.mobilitySupport.member;

import android.content.Intent;
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

import com.example.mobilitySupport.R;

public class MypageFragment extends Fragment {

    Button mypageButton, postmanageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        SearchView searchView = getActivity().findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("마이페이지");

        mypageButton = view.findViewById(R.id.mypageButton);
        postmanageButton = view.findViewById(R.id.postmanageButton);

        mypageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemberInfoActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
