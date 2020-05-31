package com.example.mobilitySupport.findRoute;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;

public class FindRouteFragment extends Fragment implements View.OnClickListener{
    MainActivity activity = null;
    Boolean routeType = true;   // true: 빠른 길찾기, false: 회피 길찾기

    ImageButton route;
    ImageButton avoidRoute;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.getSupportActionBar().show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_findroute, container, false);
        activity.getSupportActionBar().hide();

        view.findViewById(R.id.end).setOnClickListener(this);
        route = view.findViewById(R.id.route);
        route.setOnClickListener(this);
        route.setSelected(true);

        avoidRoute = view.findViewById(R.id.avoidRoute);
        avoidRoute.setOnClickListener(this);

        view.findViewById(R.id.start).setOnClickListener(this);
        view.findViewById(R.id.arrive).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.end:
                Navigation.findNavController(v).navigate(R.id.action_fragment_findRoute_to_fragment_map);
                break;
            case R.id.route:
                routeType = true;
                route.setSelected(true); avoidRoute.setSelected(false);
                break;
            case R.id.avoidRoute:
                routeType = false;
                route.setSelected(false); avoidRoute.setSelected(true);
                break;
            case R.id.start : case R.id.arrive:
                Navigation.findNavController(v).navigate(R.id.action_fragment_findRoute_to_frgment_search);
            default:
                break;
        }
    }
}
