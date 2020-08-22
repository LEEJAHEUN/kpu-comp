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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mobilitySupport.R;
import com.example.mobilitySupport.ServerRequest;

public class PlaceFragment extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener{
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

        elevator.setOnCheckedChangeListener(this);

        wheel.setOnCheckedChangeListener(this);

        imageview = (ImageView)view.findViewById(R.id.imagechoose);
        imageview.setOnClickListener(this);

        Button reset = view.findViewById(R.id.reset);
        reset.setOnClickListener(this);

        Button writeFin = view.findViewById(R.id.writeFin);
        writeFin.setOnClickListener(this);

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
            case R.id.imagechoose:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                break;
            case R.id.reset:
                spinner_availability.setAdapter(adapter_availability);
                spinner_type.setAdapter(adapter_type);
                elevator.setChecked(false);
                wheel.setChecked(false);
                // 이미지 뷰 초기화 -> 아직 남음
                break;
            case R.id.writeFin:
                String lat = PlaceFragmentArgs.fromBundle(getArguments()).getLatitude();
                String lon = PlaceFragmentArgs.fromBundle(getArguments()).getLongitude();
                id = appData.getString("ID", "");   // 로그인 정보

                if(!spinner_availability.getSelectedItem().equals("선택") &&
                        !spinner_type.getSelectedItem().equals("선택")){
                    ServerRequest serverRequest = new ServerRequest(getContext());
                    serverRequest.writePlace(id, lat, lon, spinner_availability.getSelectedItem().toString(),
                            spinner_type.getSelectedItem().toString(), elevator.getText().toString(),
                            wheel.getText().toString(), v, "postPlaceRegister.php");
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("필수 항목을 전부 입력해주십시오.")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkBox_elevator: case R.id.checkBox_wheel:
                if(isChecked)
                    buttonView.setText("있음");
                else
                    buttonView.setText("없음");
        }
    }
}
