package com.example.mobilitySupport.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapView;

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener, ResultCallback {

    private static final int RESULT_OK = -1;
    LinearLayout linearLayout;
    TMapView tMapView = null;
    boolean isLocationPermission = false;               // 권한 설정 여부
    final int CHECK_LOCATION_PERMISSION = 1;
    private static GoogleApiClient googleApiClient;     // GPS 설정
    protected LocationRequest request;
    int REQUEST_CHECK_SETTINGS = 100;                   // GPS 설정 코드
    LocationManager locationManager = null;

    int updateTime = 1000;      // 현재 위치 갱신 시간
    int updateDistance = 1;     // 현재 위치 갱신 이동 거리

    private Context context;
    MainActivity activity = null;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SearchView searchView = (SearchView)activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);

        context = container.getContext();

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
        linearLayout = view.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getActivity());

        tMapView.setSKTMapApiKey(getString(R.string.apiKey));
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setIconVisibility(true); //현재위치로 표시될 아이콘을 표시할지 여부
        tMapView.setZoomLevel(17);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);

        linearLayout.addView(tMapView);
        setGPS();

        FloatingActionButton fab_current = view.findViewById(R.id.fab_currentLocation);
        fab_current.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                setGPS();
            }
        });

        FloatingActionButton fab_post = view.findViewById(R.id.fab_post);
        fab_post.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_map_to_fragment_writePost);
            }
        });

        return view;
    }

    // 위치 리스너
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tMapView.setLocationPoint(longitude, latitude);
                tMapView.setCenterPoint(longitude, latitude);
            } else
                Toast.makeText(context, "위치 정보를 가져올 수 없습니다", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
        @Override
        public void onProviderEnabled(String provider) { }
        @Override
        public void onProviderDisabled(String provider) { }
    };

    // 현재 위치 받기
    public void setGPS() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        checkPermission();
        // 권한 승인 받았을 경우
        if (isLocationPermission == true) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this).build();
            googleApiClient.connect();
            request = LocationRequest.create();
        }
    }

    // 위치 권한 체크
    public void checkPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 권한 거부 상태
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // 권한 허용 창 요청
                requestPermissions(permissions, CHECK_LOCATION_PERMISSION);
            } else
                isLocationPermission = true;
        }
    }

    // 권한 요청 창에서 선택 시 호출
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CHECK_LOCATION_PERMISSION :
                // 권한 동의 성공
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setGPS();
                else
                    Toast.makeText(context, "위치 권한을 승인해주십시오", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(request);
        builder.setAlwaysShow(true);
        PendingResult result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }

    @Override
    public void onResult(Result result) {
        final Status status = result.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                // GPS 설정 켜진 상태
                checkPermission();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateTime, updateDistance, locationListener);
                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                // GPS 설정 꺼진 상태
                try {
                    //status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                    this.startIntentSenderForResult(status.getResolution().getIntentSender(),
                            REQUEST_CHECK_SETTINGS, null, 0,0,0,null);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHECK_SETTINGS){
            if(resultCode == RESULT_OK){
                checkPermission();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateTime, updateDistance, locationListener);
            }
            else
                Toast.makeText(context, "위치 사용 설정을 켜주십시오", Toast.LENGTH_LONG).show();
        }
    }

}
