package com.example.mobilitySupport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChoosePointFragment extends Fragment {

    MainActivity activity = null;
    Button choosePoint = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FloatingActionButton currentLocation = (FloatingActionButton)activity.findViewById(R.id.fab_currentLocation_point);
        currentLocation.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_choose_point, container, false);

        final Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("지도에서 선택");

        FloatingActionButton currentButton = (FloatingActionButton)activity.findViewById(R.id.fab_currentLocation_point);
        currentButton.setVisibility(View.VISIBLE);

        TextView address = view.findViewById(R.id.textView_address);
        activity.setPoint(choosePoint, address);    // 중심 주소 받아오기

        choosePoint = (Button)view.findViewById(R.id.choosePoint);
        choosePoint.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String postType = ChoosePointFragmentArgs.fromBundle(getArguments()).getPostType();
                if(postType.equals("road"))     // 전 프래그먼트에서 길 선택했을 경우
                    Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_writeRoad);
                else                            // 장소 선택했을 경우
                    Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_writePlace);
            }
        });

        return view;
    }
}
