package com.example.mobilitySupport.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapFragment extends Fragment {
    private SharedPreferences appData;
    String id = null;   // 받아올 사용자 아이디

    LinearLayout linearLayout;
    MainActivity activity = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FloatingActionButton currentLocation = (FloatingActionButton)activity.findViewById(R.id.fab_currentLocation);
        currentLocation.setVisibility(View.INVISIBLE);

        SearchView searchView = activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SearchView searchView = (SearchView)activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);

        appData = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        id = appData.getString("ID", "");   // 로그인 정보

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
        linearLayout = view.findViewById(R.id.linearLayoutTmap);

        FloatingActionButton currentLocation = (FloatingActionButton)activity.findViewById(R.id.fab_currentLocation);
        currentLocation.setVisibility(View.VISIBLE);

        FloatingActionButton fab_post = view.findViewById(R.id.fab_post);
        fab_post.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(id.equals(""))
                    Toast.makeText(activity.getApplicationContext(), R.string.noLogin, Toast.LENGTH_LONG).show();
                else
                    Navigation.findNavController(v).navigate(R.id.action_fragment_map_to_fragment_writePost);
            }
        });

        return view;
    }
}
