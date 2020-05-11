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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.R;

public class PlaceFragment extends Fragment {
    private SharedPreferences appData;
    String id = null;   // 받아올 사용자 아이디

    private static final int RESULT_OK = 0; // 검색
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
    Boolean checkElevator = false;  // true 일 경우 서버에 값 전달
    Boolean checkWheel = false;     //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.writepost_place, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("제보글 작성");
        appData = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);

        data_Availability = getActivity().getResources().getStringArray(R.array.spinnerArray_Availability);
        data_type = getResources().getStringArray(R.array.spinnerArray_type);

        adapter_availability = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_Availability);
        spinner_availability = (Spinner)view.findViewById(R.id.Availability);
        spinner_availability.setAdapter(adapter_availability);

        adapter_type = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_type);
        spinner_type = (Spinner)view.findViewById(R.id.spinner_type);
        spinner_type.setAdapter(adapter_type);

        elevator = view.findViewById(R.id.checkBox_elevator);
        wheel = view.findViewById(R.id.checkBox_wheel);

        elevator.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkElevator = true;
                if(isChecked)
                    elevator.setText("있음");
                else
                    elevator.setText("없음");
            }
        });

        wheel.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkWheel = true;
                if(isChecked)
                    wheel.setText("있음");
                else
                    wheel.setText("없음");
            }
        });

        imageview = (ImageView)view.findViewById(R.id.imagechoose);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        Button reset = view.findViewById(R.id.reset);
        reset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_availability.setAdapter(adapter_availability);
                spinner_type.setAdapter(adapter_type);
                elevator.setChecked(false);
                wheel.setChecked(false);
                // 이미지 뷰 초기화
                checkElevator = false; checkWheel = false;
            }
        });

        String lat = PlaceFragmentArgs.fromBundle(getArguments()).getLatitude();
        String lon = PlaceFragmentArgs.fromBundle(getArguments()).getLongitude();
        Double latitude = Double.parseDouble(lat); Double longitude = Double.parseDouble(lon);  // 위도, 경도
        id = appData.getString("ID", "");   // 로그인 정보

        Button writeFin = view.findViewById(R.id.writeFin);
        writeFin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) { // 작성 완료 버튼 클릭 시
                Navigation.findNavController(v).navigate(R.id.action_fragment_writePlace_to_fragment_map); // 화면 이동
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
        }
    }
}
