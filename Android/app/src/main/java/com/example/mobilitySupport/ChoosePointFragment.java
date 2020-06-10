package com.example.mobilitySupport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapPoint;

public class ChoosePointFragment extends Fragment {

    MainActivity activity = null;
    Button choosePoint = null;
    private SharedPreferences appData;

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
        choosePoint = (Button)view.findViewById(R.id.choosePoint);

        activity.setPoint(choosePoint, address);    // 중심 주소 표시
        choosePoint.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String postType = ChoosePointFragmentArgs.fromBundle(getArguments()).getType();
                TMapPoint center = activity.getCenter();

                appData = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = appData.edit();

                Double latitude = center.getLatitude(); Double longitude = center.getLongitude();
                if(postType.equals("road")) {    // 전 프래그먼트에서 길 선택했을 경우
                    ChoosePointFragmentDirections.ActionFragmentPointToFragmentWriteRoad roadPoint =
                            ChoosePointFragmentDirections.actionFragmentPointToFragmentWriteRoad(latitude.toString(), longitude.toString());
                    Navigation.findNavController(v).navigate(roadPoint);
                }
                else if(postType.equals("place")){                            // 장소 선택했을 경우
                    ChoosePointFragmentDirections.ActionFragmentPointToFragmentWritePlace placePoint =
                            ChoosePointFragmentDirections.actionFragmentPointToFragmentWritePlace(latitude.toString(), longitude.toString());
                    Navigation.findNavController(v).navigate(placePoint);
                }
                else if(postType.equals("start")){    // 길찾기 지도 선택
                    editor.putString("StartLat", latitude.toString());
                    editor.putString("StartLong", longitude.toString());
                    editor.apply(); editor.commit();
                    Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_findRoute);
                }
                else{
                    editor.putString("EndLat", latitude.toString());
                    editor.putString("EndLong", longitude.toString());
                    editor.apply(); editor.commit();
                    Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_findRoute);
                }
            }
        });

        return view;
    }
}
