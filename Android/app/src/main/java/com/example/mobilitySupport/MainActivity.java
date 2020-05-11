package com.example.mobilitySupport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilitySupport.findRoute.FindRouteActivity;
import com.example.mobilitySupport.login.LoginActivity;
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
import com.google.android.material.navigation.NavigationView;
import com.skt.Tmap.TMapAddressInfo;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback {

    private SharedPreferences appData;
    String id = null;   // 받아올 사용자 아이디

    private AppBarConfiguration mAppBarConfiguration;   // 네비게이션 드로어

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

    NavController navController = null;
    Intent intent = null;
    DrawerLayout drawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.fragment_map).setDrawerLayout(drawer).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View nav_header = navigationView.getHeaderView(0);
        TextView loginInfo = nav_header.findViewById(R.id.mypage_or_login);
        TextView info = nav_header.findViewById(R.id.information);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        id = appData.getString("ID", "");   // 로그인 정보

        if(!(id.equals(""))) {  // 로그인 상태일 경우
            loginInfo.setText(id+"님 로그인 중");
            info.setText("환영합니다!");
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.mypage).setVisible(true);
        }

        loginInfo.setOnClickListener(new View.OnClickListener() {   // 헤더 클릭 시
            @Override
            public void onClick(View v) {
                if(id.equals("")) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    drawer.closeDrawers();
                    navController.navigate(R.id.action_fragment_map_to_fragment_mypage);
                }
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(getString(R.string.apiKey));
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setIconVisibility(true); //현재위치로 표시될 아이콘을 표시할지 여부
        tMapView.setZoomLevel(17);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);

        linearLayout.addView(tMapView);
        setGPS();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.findRoute:
                intent = new Intent(MainActivity.this, FindRouteActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.logout:
                SharedPreferences.Editor editor = appData.edit();
                editor.clear(); editor.commit(); // 로그인 정보 삭제
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);  // 로그인 화면으로 이동
                finish();
                return true;

            case R.id.mypage:
                if(id.equals(""))
                    Toast.makeText(MainActivity.this,R.string.noLogin,Toast.LENGTH_LONG).show();
                else {
                    drawer.closeDrawers();
                    navController.navigate(R.id.action_fragment_map_to_fragment_mypage);
                }
                return true;

            case R.id.writePost:
                if(id.equals(""))
                    Toast.makeText(MainActivity.this,R.string.noLogin,Toast.LENGTH_LONG).show();
                else {
                    drawer.closeDrawers();
                    navController.navigate(R.id.action_fragment_map_to_fragment_writePost);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 눌러서 네비게이션 드로어 닫기
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawers();
        else
            super.onBackPressed();
    }

    // 현재 위치 버튼 클릭 시
    public void currentLocation(View view) { setGPS(); }

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
                Toast.makeText(MainActivity.this, "위치 정보를 가져올 수 없습니다", Toast.LENGTH_LONG).show();
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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkPermission();
        // 권한 승인 받았을 경우
        if (isLocationPermission == true) {
            googleApiClient = new GoogleApiClient.Builder(this)
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
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
            case CHECK_LOCATION_PERMISSION:
                // 권한 동의 성공
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setGPS();
                else
                    Toast.makeText(this, "위치 권한을 승인해주십시오", Toast.LENGTH_LONG).show();
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
                    status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                checkPermission();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateTime, updateDistance, locationListener);
            } else
                Toast.makeText(this, "위치 사용 설정을 켜주십시오", Toast.LENGTH_LONG).show();
        }
    }

    public void setPoint(final Button choosePoint, final TextView address) {
        TMapPoint center = tMapView.getCenterPoint();
        setAddress(address, center);
        tMapView.setOnEnableScrollWithZoomLevelListener(new TMapView.OnEnableScrollWithZoomLevelCallback() {
            @Override
            public void onEnableScrollWithZoomLevelEvent(float v, TMapPoint tMapPoint) {
                if (choosePoint != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() { choosePoint.setEnabled(false); }
                    });
                }
            }
        });

        tMapView.setOnDisableScrollWithZoomLevelListener(new TMapView.OnDisableScrollWithZoomLevelCallback() {
            @Override
            public void onDisableScrollWithZoomLevelEvent(float v, final TMapPoint tMapPoint) {
                if (choosePoint != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            choosePoint.setEnabled(true);
                            setAddress(address, tMapPoint);
                        }
                    });
                }
            }
        });
    }

    public void setAddress(final TextView address, final TMapPoint center) {
        TMapData tMapData = new TMapData();
        tMapData.reverseGeocoding(center.getLatitude(), center.getLongitude(), "A10", new TMapData.reverseGeocodingListenerCallback() {
            @Override
            public void onReverseGeocoding(TMapAddressInfo tMapAddressInfo) {
                String str = tMapAddressInfo.strCity_do+" "+tMapAddressInfo.strGu_gun+" "
                        + tMapAddressInfo.strLegalDong+" "+tMapAddressInfo.strBunji+" "
                        + tMapAddressInfo.strRi+"\n"+tMapAddressInfo.strRoadName+" "+
                        tMapAddressInfo.strBuildingIndex;
                str = str.replaceAll("null", "");
                address.setText(str);
            }
        });
    }

    public TMapPoint getCenter(){return tMapView.getCenterPoint();}
}
