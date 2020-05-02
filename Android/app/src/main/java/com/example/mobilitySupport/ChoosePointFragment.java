package com.example.mobilitySupport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

// 지도, 위치 관련 수정 예정
public class ChoosePointFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener, ResultCallback {
    // 수정 할 예정
    TMapView tMapView = null;
    private Context context;
    LocationManager locationManager = null;
    boolean isLocationPermission = false;
    private static GoogleApiClient googleApiClient;
    protected LocationRequest request;
    final int CHECK_LOCATION_PERMISSION = 1;
    int updateTime = 1000;      // 현재 위치 갱신 시간
    int updateDistance = 1;     // 현재 위치 갱신 이동 거리
    int REQUEST_CHECK_SETTINGS = 100;
    private static final int RESULT_OK = -1;

    TextView address=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_choose_point, container, false);

        SearchView searchView = getActivity().findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("지도에서 선택");

        LinearLayout layout = (LinearLayout)view.findViewById(R.id.choosePointMap);
        tMapView = new TMapView(getActivity());

        tMapView.setSKTMapApiKey(getString(R.string.apiKey));
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setZoomLevel(17);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);

        layout.addView(tMapView);

        context = container.getContext();

        setGPS();

        address = (TextView)view.findViewById(R.id.textView_address);

        TMapPoint center = tMapView.getCenterPoint();
        getPoint(center);

        tMapView.setOnEnableScrollWithZoomLevelListener(new TMapView.OnEnableScrollWithZoomLevelCallback() {
            @Override
            public void onEnableScrollWithZoomLevelEvent(float v, TMapPoint tMapPoint) {
                getPoint(tMapPoint);
            }
        });

        FloatingActionButton write = (FloatingActionButton)view.findViewById(R.id.moveWrite);
        write.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_writeRoad);
                Navigation.findNavController(v).navigate(R.id.action_fragment_point_to_fragment_writePlace);
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
