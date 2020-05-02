package com.example.mobilitySupport.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mobilitySupport.R;

public class MypageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        SearchView searchView = getActivity().findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("");

        return view;
    }
}
