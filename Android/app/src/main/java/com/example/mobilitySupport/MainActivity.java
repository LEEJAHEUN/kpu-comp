package com.example.mobilitySupport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
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

import com.example.mobilitySupport.login.LoginActivity;
import com.example.mobilitySupport.post.AllPostServerRequest;
import com.example.mobilitySupport.post.ManagePostFragment;
import com.example.mobilitySupport.post.MyPostServerRequest;
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
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;

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

    double latitude = 0, longitude = 0;

    ArrayList<TMapPoint> allPostPoint = new ArrayList();     //지도에 표시할 마커
    ArrayList<String>latList = new ArrayList<String>();     //마커에 사용할 위도, 경도
    ArrayList<String>lonList = new ArrayList<String>();

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

        //메인 화면(app_bar_main)의 지도 부분 레이아웃을 가져와 대입
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        //지도 생성
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(getString(R.string.apiKey));
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setIconVisibility(true); //현재위치로 표시될 아이콘을 표시할지 여부
        //지도 띄울 때 확대 정도
        tMapView.setZoomLevel(17);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);

/*        //서버 연결, 위치 좌표 받아옴(장소)
        AllPostServerRequest allPostServerRequest = new AllPostServerRequest(MainActivity.this);
        allPostServerRequest.getAllPost(tMapView,"getAllPost.php");*/

        //서버로부터 받아온 제보글이 있는지 확인함
        Intent intent = getIntent();
        String[] allPostList = intent.getStringArrayExtra("allPostList");

        //받아온 제보글이 없으면 서버로부터 다시 받아옴
        if(allPostList==null){
            //서버 연결, 위치 좌표 받아옴(장소)
            //주석처리하고도 마커 뜨는지 확인
            AllPostServerRequest allPostServerRequest = new AllPostServerRequest(MainActivity.this);
            allPostServerRequest.getAllPost(tMapView,"getAllPost.php");

            intent = getIntent();
            allPostList = intent.getStringArrayExtra("allPlaceList");
        }

        if(allPostList !=null){
//            for(int i=0;i<allPostList.length;i++){
//                System.out.println("서버로부터 받아온 제보글 목록: "+allPostList[i]);
//            }
            //받아온 좌표를 경도, 위도로 분리(위도, 경도가 장소->길 순으로 저장됨)
            for(int i=0;i<(allPostList.length);){
                latList.add(allPostList[i]);
                lonList.add(allPostList[i+1]);

                i+=2;
            }
            /*System.out.println("나눈 위도, 경도: ");
            //위도, 경도로 나뉘었는지 확인
            for(String s : latList){ System.out.println(s); }
            for(String s : lonList){ System.out.println(s); }*/
        }

        //제보글 위에 마커 표시
        addPostMarker();

        //마커 클릭 이벤트 설정
        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> markerList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markerList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                int tmapItemSize = markerList.size();

                for(int i=0;i<tmapItemSize;i++){
/*                    Log.e("express","onPressUpEvent="+markerList.get(i).getID());
                    Log.e("express","onPressUpEvent="+markerList.get(i).getTMapPoint());
                    Log.e("express","onPressUpEvent="+markerList.get(i).getName());*/
                    //클릭한 마커의 좌표를 얻어옴
                    final TMapPoint itemPoint = markerList.get(i).getTMapPoint();

/*                    //경도,위도를 번들에 저장
                    //(같은 위치의 제보글 목록을 보여주기 위해) 길, 장소 선택 프래그먼트로 이동
                    ManagePostFragment managePostFragment = new ManagePostFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("markerLat",itemPoint.getLatitude());
                    bundle.putDouble("markerLon",itemPoint.getLongitude());
                    managePostFragment.setArguments(bundle);
                    System.out.println("마커 클릭 시 전달되는 위치:"+bundle);
                    navController.navigate(R.id.action_fragment_map_to_managePostFragment,bundle);*/

                    //클릭한 좌표의 제보글을 찾기 위해 번들을 MyPostServerRequest로 전송(final 맞는지 확인)
                    final String markerLat = Double.toString(itemPoint.getLatitude());
                    final String markerLon = Double.toString(itemPoint.getLongitude());
                    System.out.println("마커 클릭 시 전달되는 위치:"+markerLat+", "+markerLon);
                    MyPostServerRequest myPostServerRequest = new MyPostServerRequest(MainActivity.this);
                    myPostServerRequest.getPostRoad(markerLat, markerLon, tMapView, "readPostRoad.php");
                    myPostServerRequest.getPostPlace(markerLat, markerLon, tMapView, "readPostPlace.php");
                }

                return false;
            }
        });

        //화면에 지도 표시
        linearLayout.addView(tMapView);
        setGPS();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.findRoute:
                drawer.closeDrawers();
                navController.navigate(R.id.action_fragment_map_to_fragment_findRoute);
                return true;

            case R.id.logout:
                SharedPreferences.Editor editor = appData.edit();
                editor.remove("ID"); editor.remove("SAVE_LOGIN_DATA"); // 로그인 정보 삭제
                editor.commit();
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

            //앱 정보 선택 시
            case R.id.appInformation:
                drawer.closeDrawers();
                navController.navigate(R.id.action_fragment_map_to_appInfoFragment);
                return true;

            //알림 선택 시
            case R.id.fragment_alarm:
                final Switch switchAlarm = (Switch)findViewById(R.id.switchAlarm);

                switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //알림이 체크되어있으면 마커 표시
                        if(switchAlarm.isChecked()){
                            addPostMarker();
                        }
                        else{
                            removePostMarker();
                        }
                    }
                });

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
                latitude = location.getLatitude();
                longitude = location.getLongitude();
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

    //지도에서 위치 선택시 하단에 해당좌표의 주소 표시
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
                final String finalStr = str;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        address.setText(finalStr);
                    }
                });
            }
        });
    }

    //길찾기 시 선택한 위치를 주소로 변환, 출발지/목적지에 표시
    public void setRouteAddress(final TextView address, final TMapPoint point) {
        TMapData tMapData = new TMapData();
        tMapData.reverseGeocoding(point.getLatitude(), point.getLongitude(), "A10", new TMapData.reverseGeocodingListenerCallback() {
            @Override
            public void onReverseGeocoding(TMapAddressInfo tMapAddressInfo) {
                String str = tMapAddressInfo.strCity_do+" "+tMapAddressInfo.strGu_gun+" "
                        + tMapAddressInfo.strLegalDong+" " + tMapAddressInfo.strRi+" "
                        +tMapAddressInfo.strRoadName+" "+ tMapAddressInfo.strBuildingIndex;
                str = str.replaceAll("null", "");
                final String finalStr = str;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        address.setText(finalStr);
                    }
                });
            }
        });
    }

    //출발지, 목적지를 받아 (최단)경로 표시
    public void getShortestPath(final TMapPoint start, TMapPoint arrive, ArrayList<TMapPoint> passList, int searchOption) {
        TMapData data = new TMapData();

        final TMapPoint finalStart = start;
        final TMapPoint finalArrive = arrive;

        //보행자 (최단)경로를 요청함(경로 종류, 출발지, 도착지, 검색결과를 받는 인터페이스 함수)
        data.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, arrive, passList, searchOption, new TMapData.FindPathDataListenerCallback() {
            //보행자 (최단)경로 생성
            @Override
            public void onFindPathData(final TMapPolyLine path) {
                //UiThread 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //보여줄 경로(선)의 두께, 색상 설정
                        path.setLineWidth(5);
                        path.setLineColor(Color.RED);

                        //경로를 지도에 표시함
                        tMapView.addTMapPath(path);

                        //결과에 맞춰 중심 좌표, 확대 정도 변경
                        ArrayList<TMapPoint> point = new ArrayList<>();
                        point.add(finalStart); point.add(finalArrive);

                        TMapInfo info = tMapView.getDisplayTMapInfo(point);
                        tMapView.setCenterPoint(info.getTMapPoint().getLongitude(),info.getTMapPoint().getLatitude());
                        //+0.0020
                        tMapView.setZoomLevel(info.getTMapZoomLevel()-1);
                    }
                });
            }
        });
    }

    public void getAvoidPath(final List<TMapPoint> list, final ArrayList<TMapPoint> point) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TMapPolyLine tMapPolyLine = new TMapPolyLine();
                tMapPolyLine.setLineColor(Color.RED);
                tMapPolyLine.setLineWidth(5);

                for(int i=0;i<list.size();i++){
                    tMapPolyLine.addLinePoint(list.get(i));
                }

                tMapView.addTMapPath(tMapPolyLine);

                addPostMarker();

                TMapInfo info = tMapView.getDisplayTMapInfo(point);
                tMapView.setCenterPoint(info.getTMapPoint().getLongitude(),info.getTMapPoint().getLatitude());
                tMapView.setZoomLevel(info.getTMapZoomLevel()-1);
            }
        });
    }

    //제보글 위치의 마커를 지도에 표시(다중마커)
    public void addPostMarker(){
        for(int i=0;i<latList.size();i++){
            //제보글 위치의 위도, 경도를 받아와 저장
            TMapPoint postPoint = convDouble(latList.get(i),lonList.get(i));
            allPostPoint.add(postPoint);
        }

        //마커목록에 제대로 add되었는지 확인
//        for(TMapPoint t : allPostPoint){
//            System.out.println("목록에 저장된 tMapPoint: " + t);
//        }

        //마커 아이콘 지정
        final Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_road);

        //목록에 저장된 만큼 마커 추가
        for(int i=0;i<allPostPoint.size();i++){
            TMapMarkerItem postMarker = new TMapMarkerItem();
            postMarker.setIcon(bitmap);//마커 아이콘 지정
            postMarker.setPosition(0.5f,1.0f);  //마커가 표시되는 위치를 이미지의 중앙,하단으로 설정
//            postMarker.setTMapPoint((TMapPoint) allPostPoint.get(i));   //allPostPoint에 저장된 tMapPoint를 중심점으로 사용
            postMarker.setTMapPoint(allPostPoint.get(i));   //allPostPoint에 저장된 tMapPoint를 중심점으로 사용
            postMarker.setName("Marker"+i);   //마커 타이틀 지정
            tMapView.addMarkerItem("postMarker"+i, postMarker);    //지도에 마커 추가
//            System.out.println("추가한 마커id: "+postMarker.getID()+"좌표: "+postMarker.getTMapPoint());
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

    //지도에 표시한 경로 삭제
    public void removePath(){ tMapView.removeTMapPath(); }
    public TMapPoint getCenter(){ return tMapView.getCenterPoint(); }
    public TMapPoint getLocation(){ return new TMapPoint(latitude, longitude); }

    //지도에 표시한 마커 삭제
    public void removePostMarker(){tMapView.removeAllMarkerItem(); }
}
