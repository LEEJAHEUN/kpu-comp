package com.example.mobilitySupport.post;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;

public class ChoosePostTypeFragment extends Fragment {
    MainActivity activity = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_choose_posttype, container, false);

        SearchView searchView = activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Button buttonRoad = view.findViewById(R.id.writeRoad);
        Button buttonPlace = view.findViewById(R.id.wrtiePlace);

        buttonRoad.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {   // 버튼 클릭시 다음 프래그먼트에 선택 타입 전달
                ChoosePostTypeFragmentDirections.ActionFragmentWritePostToFragmentPoint action
                        = ChoosePostTypeFragmentDirections.actionFragmentWritePostToFragmentPoint("road");
                Navigation.findNavController(v).navigate(action);
            }
        });

        buttonPlace.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {   // 위와 동일
                ChoosePostTypeFragmentDirections.ActionFragmentWritePostToFragmentPoint action
                        = ChoosePostTypeFragmentDirections.actionFragmentWritePostToFragmentPoint("place");
                Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }
}
