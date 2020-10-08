package com.example.mobilitySupport.post;

//장소 제보글 리스트뷰의 아이템 데이터
public class PlaceListItem {
    private String postNum,writerID,availability,type,elevator,wheel;
    private String latitude, longitude;

    public void setPostNum(String selectNum) {
        postNum = selectNum;
    }
    public void setWriterID(String selectWriterID) {
        writerID = selectWriterID;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAvailability(String tv1){
        availability = tv1;
    }
    public void setType(String tv2){
        type = tv2;
    }
    public void setElevator(String tv3){
        elevator = tv3;
    }
    public void setWheel(String tv4){
        wheel = tv4;
    }

    public String getPostNum() {
        return this.postNum;
    }
    public String getWriterID() {
        return this.writerID;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public String getLongitude() {
        return this.longitude;
    }

    public String getAvailability(){
        return this.availability;
    }
    public String getType(){
        return this.type;
    }
    public String getElevator(){
        return this.elevator;
    }
    public String getWheel(){
        return this.wheel;
    }

}
