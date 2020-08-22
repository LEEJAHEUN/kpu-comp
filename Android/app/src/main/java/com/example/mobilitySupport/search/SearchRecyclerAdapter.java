package com.example.mobilitySupport.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;
import com.skt.Tmap.TMapPOIItem;

import java.util.ArrayList;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ItemViewHolder> {

    private ArrayList<TMapPOIItem> list = new ArrayList<>();
    private SharedPreferences appData;
    MainActivity activity;
    private String type = null;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(ArrayList<TMapPOIItem> items, MainActivity activity, String type){
        list.addAll(items);
        this.activity = activity;
        this.type = type;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView address;
        private TextView distance;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.poiName);
            address = itemView.findViewById(R.id.poiAddress);
            distance = itemView.findViewById(R.id.distance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appData = activity.getSharedPreferences("appData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = appData.edit();
                    String latitude = list.get(getAdapterPosition()).noorLat;
                    String longitude = list.get(getAdapterPosition()).noorLon;

                    if(type.equals("start")){    // 길찾기 지도 선택
                        editor.putString("StartLat", latitude);
                        editor.putString("StartLong", longitude);
                    }
                    else{
                        editor.putString("EndLat", latitude);
                        editor.putString("EndLong", longitude);
                    }
                    editor.apply(); editor.commit();
                    Navigation.findNavController(v).navigate(R.id.action_fragment_search_result_to_fragment_findRoute);
                }
            });
        }

        void onBind(TMapPOIItem item){
            double radius = Double.parseDouble(item.radius);
            String firstAddress = item.upperAddrName + " " + item.middleAddrName + " " + item.lowerAddrName
                    + " " + item.roadName + " " + item.buildingNo1;
            String secondAddress = item.buildingNo2;

            firstAddress = firstAddress.replaceAll("null", "");
            if(secondAddress == null)
                address.setText(firstAddress);
            else if(secondAddress.equals("0")){
                secondAddress = secondAddress.replaceAll("0", "");
                address.setText(firstAddress + secondAddress);
            }
            else
                address.setText(firstAddress + "-" + secondAddress);

            if(radius < 1)
                distance.setText(Math.round(radius * 1000) + "m");
            else
                distance.setText(Math.round(radius * 100) / 100.0 + "km");

            name.setText(item.getPOIName());
        }
    }
}
