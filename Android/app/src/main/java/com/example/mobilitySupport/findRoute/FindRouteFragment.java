package com.example.mobilitySupport.findRoute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.skt.Tmap.TMapPoint;

public class FindRouteFragment extends Fragment implements View.OnClickListener{
    MainActivity activity = null;
    Boolean routeType = true;   // true: 빠른 길찾기, false: 회피 길찾기

    ImageButton route;
    ImageButton avoidRoute;

    private SharedPreferences appData;
    String startLat, startLong, endLat, endLong;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.getSupportActionBar().show();
        activity.removePath();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_findroute, container, false);
        //메인액티비티의 액션바 숨김
        activity.getSupportActionBar().hide();

        view.findViewById(R.id.end).setOnClickListener(this);
        route = view.findViewById(R.id.route);
        route.setOnClickListener(this);
        route.setSelected(true);

        avoidRoute = view.findViewById(R.id.avoidRoute);
        avoidRoute.setOnClickListener(this);

        view.findViewById(R.id.start).setOnClickListener(this);
        view.findViewById(R.id.arrive).setOnClickListener(this);

        appData = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        getPoint(); // 지정된 point 가져옴 (String 이기 때문에 Double 로 변환 필요)
        // 해당 프래그먼트 불러올 때 point 값 확인 하여 모든 값이 지정되어 있는 경우 길찾기 시작 -> 화면 나갈 때 저장된 값 삭제 필요
        // 둘 중 하나 이상 지정된 경우 주소 표시

        TextView start = view.findViewById(R.id.start);
        TextView end = view.findViewById(R.id.arrive);

        TMapPoint start_point = null;
        TMapPoint end_point = null;

        //출발지만 null이 아닌 경우
        if(!(startLat.equals(""))){
            //가져온 값을 double로 변환
            start_point = convDouble(startLat, startLong);
            activity.setRouteAddress(start, start_point);

        }

        //도착지만 null이 아닌 경우
        if(!(endLat.equals(""))) {
            //가져온 값을 double로 변환
            end_point = convDouble(endLat, endLong);
            activity.setRouteAddress(end, end_point);
        }

        //출발지, 도착지 모두 null이 아닌 경우
        if( (!(startLat.equals("")) && (!(endLat.equals("")))) ){
            view.findViewById(R.id.rootHistory).setVisibility(View.INVISIBLE);

            //최단거리 구함
            activity.getShortestPath(start_point, end_point);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.end:
                SharedPreferences.Editor editor = appData.edit();
                editor.remove("StartLat"); editor.remove("StartLong");
                editor.remove("EndLat"); editor.remove("EndLong");
                editor.commit();    // 길찾기 종료 시 저장된 좌표값 삭제

                // 메인화면으로 이동
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
            case R.id.start :
                FindRouteFragmentDirections.ActionFragmentFindRouteToFragmentSearch actionFragmentFindRouteToFragmentSearchStart
                        = FindRouteFragmentDirections.actionFragmentFindRouteToFragmentSearch("start");
                Navigation.findNavController(v).navigate(actionFragmentFindRouteToFragmentSearchStart);
                break;
            case R.id.arrive:
                FindRouteFragmentDirections.ActionFragmentFindRouteToFragmentSearch actionFragmentFindRouteToFragmentSearchArrive
                        = FindRouteFragmentDirections.actionFragmentFindRouteToFragmentSearch("arrive");
                Navigation.findNavController(v).navigate(actionFragmentFindRouteToFragmentSearchArrive);
                break;
            default:
                break;
        }
    }

    //문자열을 실수로 형변환
    public TMapPoint convDouble(String sLat, String sLong){
        //double형으로 형변환
        double dLat = Double.parseDouble(sLat);
        double dLong = Double.parseDouble(sLong);
        TMapPoint point = new TMapPoint(dLat, dLong);
        return  point;
    }

    //출발지, 목적지의 좌표(위도, 경도)가져옴
    public void getPoint(){
        startLat = appData.getString("StartLat", "");
        startLong = appData.getString("StartLong", "");
        endLat = appData.getString("EndLat", "");
        endLong = appData.getString("EndLong", "");
    }


}
