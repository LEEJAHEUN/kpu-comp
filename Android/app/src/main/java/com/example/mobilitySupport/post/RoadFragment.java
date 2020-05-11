package com.example.mobilitySupport.post;

import android.content.Intent;
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

public class RoadFragment extends Fragment {

    private static final int RESULT_OK = 0; // 검색
    String[] data_Availability = null;
    String[] data_type = null;
    String[] data_angle = null;
    ImageView imageview = null;
    Spinner spinner_availability = null;
    Spinner spinner_type = null;
    Spinner spinner_angle = null;
    ArrayAdapter<String> adapter_availability = null;
    ArrayAdapter<String> adapter_angle = null;
    ArrayAdapter<String> adapter_type = null;
    CheckBox stairs = null;
    CheckBox breakage = null;

    private final int GET_GALLERY_IMAGE = 200;
    Boolean checkStairs = false;    // true 일 경우 서버에 값 전달
    Boolean checkBreakage = false;  //

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.writepost_road, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("제보글 작성");

        data_Availability = getActivity().getResources().getStringArray(R.array.spinnerArray_Availability);
        data_type = getResources().getStringArray(R.array.spinnerArray_type);
        data_angle = getResources().getStringArray(R.array.spinnerArray_angle);

        adapter_availability = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_Availability);
        spinner_availability = (Spinner)view.findViewById(R.id.availability);
        spinner_availability.setAdapter(adapter_availability);

        adapter_angle = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_angle);
        spinner_angle = (Spinner)view.findViewById(R.id.angle);
        spinner_angle.setAdapter(adapter_angle);

        adapter_type = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_type);
        spinner_type = (Spinner)view.findViewById(R.id.spinner_type);
        spinner_type.setAdapter(adapter_type);

        stairs = view.findViewById(R.id.checkBox_stairs);
        breakage = view.findViewById(R.id.checkBox_breakage);

        stairs.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStairs = true;
                if(isChecked)
                    stairs.setText("있음");
                else
                    stairs.setText("없음");
            }
        });

        breakage.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBreakage = true;
                if(isChecked)
                    breakage.setText("있음");
                else
                    breakage.setText("없음");
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
                spinner_angle.setAdapter(adapter_angle);
                spinner_type.setAdapter(adapter_type);
                stairs.setChecked(false);
                breakage.setChecked(false);
                // 이미지뷰 초기화
                checkStairs = false; checkBreakage = false;
            }
        });

        Button writeFin = view.findViewById(R.id.writeFin);
        writeFin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_writeRoad_to_fragment_map);
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
