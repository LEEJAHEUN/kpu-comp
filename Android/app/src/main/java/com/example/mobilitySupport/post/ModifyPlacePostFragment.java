package com.example.mobilitySupport.post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mobilitySupport.R;

public class ModifyPlacePostFragment extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener {
    private SharedPreferences appData;
    String id = null;   // 받아올 사용자 아이디

    private static final int RESULT_OK = 0; // 검색

    //R.layout.updatepost_place의 위젯에 대입할 변수들
    String[] data_Availability = null;
    String[] data_type = null;
    ImageView imageview = null;
    Spinner spinner_availability = null;
    Spinner spinner_type = null;
    CheckBox elevator = null;
    CheckBox wheel = null;
    ArrayAdapter<String> adapter_availability = null;
    ArrayAdapter<String> adapter_type = null;

    private final int GET_GALLERY_IMAGE = 200;
    Button reset, modifyFin;

    //번들로부터 받은 값을 저장할 변수
    private String[] selectPost = new String[8];
/*    private String selectPostNum = null;
    private String selectLatitude = null;
    private String selectLongitude = null;*/

    //프래그먼트 내에서 값을 지정할때 사용할 변수
    private String postNumData = null;
    private String availabilityData = null;
    private String typeData = null;
    private String elevatorData = null;
    private String wheelData = null;

    private String latitudeData = null;
    private String longitudeData = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.updatepost_place,container,false);

        appData = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);

        //각 변수에 위젯 대입
        data_Availability = getActivity().getResources().getStringArray(R.array.spinnerArray_Availability);
        data_type = getResources().getStringArray(R.array.spinnerArray_type);

        adapter_availability = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_Availability);
        spinner_availability = (Spinner)view.findViewById(R.id.availability_update_place);
        spinner_availability.setAdapter(adapter_availability);

        adapter_type = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_type);
        spinner_type = (Spinner)view.findViewById(R.id.spinner_type_update_place);
        spinner_type.setAdapter(adapter_type);

        //엘리베이터 유무, 휠체어 경사로 부분은 체크박스
        //변수에 위젯 대입, 리스너 부착
        elevator = view.findViewById(R.id.checkBox_elevator_up_place);
        elevator.setOnCheckedChangeListener(this);

        wheel = view.findViewById(R.id.checkBox_wheel_up_place);
        wheel.setOnCheckedChangeListener(this);

        imageview = (ImageView)view.findViewById(R.id.imagechoose_update_place);
        imageview.setOnClickListener(this);

        reset = view.findViewById(R.id.reset_up_place);
        reset.setOnClickListener(this);

        modifyFin = view.findViewById(R.id.modifyFin_place);
        modifyFin.setOnClickListener(this);

        //CheckPlacePostActivity로부터 전달받은 번들
        Bundle bundle = getArguments();
        if(bundle != null){
            //ModifyRoadPostActivity에서 보낸 번들 값을 받음
            selectPost = bundle.getStringArray("selectPost");
/*            selectPostNum = bundle.getString("selectPostNum");
            selectLatitude = bundle.getString("selectLatitude");
            selectLongitude = bundle.getString("selectLongitude");*/

            System.out.println("수정 프래그먼트에서 받은 제보글번호"+selectPost[0]);
            System.out.println("수정 프래그먼트에서 받은 작성자"+selectPost[1]);
            System.out.println("수정 프래그먼트에서 받은 위도"+selectPost[6]);
            System.out.println("수정 프래그먼트에서 받은 경도"+selectPost[7]);

            //받아온 번들 값을 변수에 대입
            availabilityData = selectPost[2];
            typeData = selectPost[3];
            elevatorData = selectPost[4];
            wheelData = selectPost[5];

            postNumData = selectPost[0];
            latitudeData = selectPost[6];
            longitudeData = selectPost[7];

            //이용가능 정도 설정(1/2/3)
            if(availabilityData.equals("1")){
                spinner_availability.setSelection(1);
            }
            else if(availabilityData.equals("2")){
                spinner_availability.setSelection(2);
            }
            else{
                spinner_availability.setSelection(3);
            }

            //교통약자 유형 설정(임산부/휠체어 이용자/보조기구 이용자)
            if(typeData.equals("임산부")){
                spinner_type.setSelection(1);
            }
            else if(typeData.equals("휠체어 이용자")){
                spinner_type.setSelection(2);
            }
            else{
                spinner_type.setSelection(3);
            }

            //엘리베이터 유무(있음/없음)
            //있음이면 check
            if(elevatorData.equals("있음")){
                elevator.setChecked(true);
            }

            //휠체어 경사로 유무(있음/없음)
            if(wheelData.equals("있음")){
                wheel.setChecked(true);
            }
        }
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagechoose_update_place:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                break;
            case R.id.reset_up_place:
                spinner_availability.setAdapter(adapter_availability);
                spinner_type.setAdapter(adapter_type);
                elevator.setChecked(false);
                wheel.setChecked(false);
                // 이미지뷰 초기화
                break;
            case R.id.modifyFin_place:
                String lat = latitudeData;
                String lon = longitudeData;

                id = appData.getString("ID", "");   // 로그인 정보

                if(!spinner_availability.getSelectedItem().equals("선택") &&
                        !spinner_type.getSelectedItem().equals("선택")) {

                    PostServerRequest postServerRequest = new PostServerRequest(getContext());
                    postServerRequest.modifyPostPlace(postNumData,id, lat, lon, spinner_availability.getSelectedItem().toString(),
                            spinner_type.getSelectedItem().toString(), elevator.getText().toString(),
                            wheel.getText().toString(), v, "modifypostPlace.php");
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("필수 항목을 전부 입력해주십시오.")
                            .setNegativeButton("확인", null)
                            .create().show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            buttonView.setText("있음");
        else
            buttonView.setText("없음");
    }
}

