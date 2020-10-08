package com.example.mobilitySupport.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobilitySupport.MainActivity;
import com.example.mobilitySupport.R;

import java.util.ArrayList;


//도로 제보글 목록 어댑터
public class RoadListViewAdapter extends BaseAdapter {

    //adapter에 추가된 데이터를 저장하기 위한 arraylist
    private ArrayList<RoadListItem> roadListItem = new ArrayList<RoadListItem>();

    //listViewAdapter의 생성자
    public RoadListViewAdapter(){}

    //adapter에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return roadListItem.size();
    }


    //지정한 position에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return roadListItem.get(position);
    }

    //지정한 position에 있는 데이터와 관계된 아이템(row)의 id를 return
    @Override
    public long getItemId(int position) {
        return position;
    }

    //position에 위치한 데이터를 화면에 출력하는데 사용할 view 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //리스트뷰 아이템의 layout을 인플레이트. convertView에 대한 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.roadlist_item,parent,false);
        }

        //화면에 표시할 view로부터 위젯에 대한 참조 획득
        TextView availabilityView = (TextView)convertView.findViewById(R.id.tv1);
        TextView typeView = (TextView)convertView.findViewById(R.id.tv2);
        TextView angleView = (TextView)convertView.findViewById(R.id.tv3);
        TextView stairsView = (TextView)convertView.findViewById(R.id.tv4);
        TextView breakageView = (TextView)convertView.findViewById(R.id.tv5);

        TextView postNumView = (TextView)convertView.findViewById(R.id.selectNum);
        TextView writerView = (TextView)convertView.findViewById(R.id.selectWriterID);
        TextView latitudeView = (TextView)convertView.findViewById(R.id.selectLatitude);
        TextView longitudeView = (TextView)convertView.findViewById(R.id.selectLongitude);

        //roadListItem에서 position에 위치한 데이터 참조 획득
        RoadListItem roadItem = roadListItem.get(pos);

        //아이템 내 각 위젯에 데이터 반영
        availabilityView.setText(roadItem.getAvailability());
        typeView.setText(roadItem.getType());
        angleView.setText(roadItem.getAngle());
        stairsView.setText(roadItem.getStairs());
        breakageView.setText(roadItem.getBreakage());

        postNumView.setText(roadItem.getPostNum());
        writerView.setText(roadItem.getWriterID());
        latitudeView.setText(roadItem.getLatitude());
        longitudeView.setText(roadItem.getLongitude());

        return convertView;
    }

    //아이템 데이터 추가 함수.(수정가능)
    public  void  addItem(String selectNum, String writerID, String availability, String type, String angle, String stairs, String breakage, String latitude, String longitude){
        RoadListItem item = new RoadListItem();

        item.setAvailability(availability);
        item.setType(type);
        item.setAngle(angle);
        item.setStairs(stairs);
        item.setBreakage(breakage);

        item.setPostNum(selectNum);
        item.setWriterID(writerID);
        item.setLatitude(latitude);
        item.setLongitude(longitude);

        roadListItem.add(item);
    }
}
