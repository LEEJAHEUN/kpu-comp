package com.example.mobilitySupport.findRoute;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchFragment extends Fragment implements View.OnClickListener{
    MainActivity activity = null;
    SearchView searchView = null;
    ImageView closeButton = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        SearchView searchView = activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        activity.findViewById(R.id.appBarLayout).setOutlineProvider(null);

        view.findViewById(R.id.choosemappoint).setOnClickListener(this);
        view.findViewById(R.id.mylocation).setOnClickListener(this);

        searchView = (SearchView)activity.findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.setIconified(false);
        searchView.setOnClickListener(this);
        closeButton = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty())
                    closeButton.setVisibility(View.INVISIBLE);
                else
                    closeButton.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*
            case R.id.search_view:
                searchView.setIconified(false);
                searchView.onActionViewExpanded();
             */
            case R.id.mylocation:
                break;
            case R.id.choosemappoint:
                Navigation.findNavController(v).navigate(R.id.action_frgment_search_to_fragment_point);
        }
    }


}
