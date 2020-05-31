package com.example.mobilitySupport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

public class ChoosePointFragment extends Fragment  {

    TMapView tMapView = null;
    TextView address=null;
    Context context = null;
    MainActivity activity = null;

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

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("지도에서 선택");

        FloatingActionButton currentButton = (FloatingActionButton)activity.findViewById(R.id.fab_currentLocation_point);
        currentButton.setVisibility(View.VISIBLE);

        Button choosePoint = (Button)view.findViewById(R.id.choosePoint);
        choosePoint.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_writeRoad);
            }
        });

        return view;
    }

    public void getPoint(TMapPoint center){
        TMapMarkerItem mapMarkerItem = new TMapMarkerItem();
        mapMarkerItem.setTMapPoint(center);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        mapMarkerItem.setIcon(bitmap);

        tMapView.addMarkerItem(mapMarkerItem.getID(), mapMarkerItem);

        TMapData tMapData = new TMapData();
        tMapData.convertGpsToAddress(center.getLatitude(), center.getLongitude(),
                new TMapData.ConvertGPSToAddressListenerCallback() {
                    @Override
                    public void onConvertToGPSToAddress(String s) {
                        address.setText(s);
                    }
                });
    }

}
