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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WritePostFragment extends Fragment {

    public String text = null;
    private Context context;
    ViewGroup view = null;
    MainActivity activity = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        view = (ViewGroup) inflater.inflate(R.layout.fragment_choose_posttype, container, false);

        SearchView searchView = activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Button buttonRoad = view.findViewById(R.id.writeRoad);
        Button buttonPlace = view.findViewById(R.id.wrtiePlace);

        buttonRoad.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_writePost_to_fragment_point);
            }
        });

        return view;
    }
}
