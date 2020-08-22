package com.example.mobilitySupport.findRoute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.skt.Tmap.TMapPoint;

public class FindRouteFragment extends Fragment implements View.OnClickListener {
    MainActivity activity = null;
    Boolean routeType = true;   // true: 빠른 길찾기, false: 회피 길찾기

    ImageButton route;
    ImageButton avoidRoute;
    TextView start;
    TextView end;

    TMapPoint start_point = null;
    TMapPoint end_point = null;

    RecyclerView rootHistory;

    private SharedPreferences appData;
    SharedPreferences.Editor editor;
    String startLat, startLong, endLat, endLong;

    SearchView searchView;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                editor.remove("StartLat"); editor.remove("StartLong");
                editor.remove("EndLat"); editor.remove("EndLong");
                editor.commit();    // 길찾기 종료 시 저장된 좌표값 삭제
                Navigation.findNavController(getView()).navigate(R.id.action_fragment_findRoute_to_fragment_map);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_findroute, container, false);
        //메인액티비티의 액션바 숨김
        activity.getSupportActionBar().hide();

        searchView = (SearchView)activity.findViewById(R.id.search_view);
        searchView.setQuery("", false);

        rootHistory = view.findViewById(R.id.rootHistory);
        view.findViewById(R.id.end).setOnClickListener(this);
        route = view.findViewById(R.id.route);
        route.setOnClickListener(this);
        route.setSelected(true);

        avoidRoute = view.findViewById(R.id.avoidRoute);
        avoidRoute.setOnClickListener(this);

        start = view.findViewById(R.id.start);
        start.setOnClickListener(this);
        end = view.findViewById(R.id.arrive);
        end.setOnClickListener(this);

        view.findViewById(R.id.change).setOnClickListener(this);

        appData = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
        editor = appData.edit();


        setFindRoute();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.end:
                editor.remove("StartLat"); editor.remove("StartLong");
                editor.remove("EndLat"); editor.remove("EndLong");
                editor.commit();    // 길찾기 종료 시 저장된 좌표값 삭제
                // 메인화면으로 이동
                Navigation.findNavController(v).navigate(R.id.action_fragment_findRoute_to_fragment_map);
                searchView.clearFocus(); searchView.setIconified(true);
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
            case R.id.change:
                editor.putString("StartLat", endLat);
                editor.putString("StartLong", endLong);
                editor.putString("EndLat", startLat);
                editor.putString("EndLong", startLong);
                editor.apply(); editor.commit();

                if(startLat.equals(""))
                    end.setText("");
                if(endLat.equals(""))
                    start.setText("");
                setFindRoute();
                break;
        }
    }

    public void setFindRoute(){
        getPoint(); // 지정된 point 가져옴 (String 이기 때문에 Double 로 변환 필요)
        // 해당 프래그먼트 불러올 때 point 값 확인 하여 모든 값이 지정되어 있는 경우 길찾기 시작 -> 화면 나갈 때 저장된 값 삭제 필요
        // 둘 중 하나 이상 지정된 경우 주소 표시

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
            rootHistory.setVisibility(View.INVISIBLE);
            //최단거리 구함
            activity.getShortestPath(start_point, end_point);
        }
    }


    //문자열을 실수로 형변환
    public TMapPoint convDouble(String sLat, String sLong){
        //double형으로 형변환
        double dLat = Double.parseDouble(sLat);
        double dLong = Double.parseDouble(sLong);
        TMapPoint point = new TMapPoint(dLat, dLong);
        return point;
    }

    //출발지, 목적지의 좌표(위도, 경도)가져옴
    public void getPoint(){
        startLat = appData.getString("StartLat", "");
        startLong = appData.getString("StartLong", "");
        endLat = appData.getString("EndLat", "");
        endLong = appData.getString("EndLong", "");
    }

}