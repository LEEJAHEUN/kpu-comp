package com.example.mobilitySupport.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    String[] data_twoChoice = null;
    ImageView imageview = null;
    Spinner spinner_availability = null;
    Spinner spinner_stairs = null;
    Spinner spinner_angle = null;
    Spinner spinner_breakage = null;
    ArrayAdapter<String> adapter_availability = null;
    ArrayAdapter<String> adapter_twoChoice = null;

    private final int GET_GALLERY_IMAGE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.writepost_road, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("제보글 작성");

        data_Availability = getActivity().getResources().getStringArray(R.array.spinnerArray_Availability);
        data_twoChoice = getResources().getStringArray(R.array.spinnerArray_twoChoice);

        adapter_availability = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, data_Availability);
        spinner_availability = (Spinner)view.findViewById(R.id.availability);
        spinner_availability.setAdapter(adapter_availability);

        adapter_twoChoice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,data_twoChoice);

        spinner_angle = (Spinner)view.findViewById(R.id.angle);
        spinner_angle.setAdapter(adapter_twoChoice);

        spinner_breakage = (Spinner)view.findViewById(R.id.breakage);
        spinner_breakage.setAdapter(adapter_twoChoice);

        spinner_stairs = (Spinner)view.findViewById(R.id.stairs);
        spinner_stairs.setAdapter(adapter_twoChoice);

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
                spinner_stairs.setAdapter(adapter_twoChoice);
                spinner_breakage.setAdapter(adapter_twoChoice);
                spinner_angle.setAdapter(adapter_twoChoice);
                // 이미지뷰 초기화
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
}
