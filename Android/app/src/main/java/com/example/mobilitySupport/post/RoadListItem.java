package com.example.mobilitySupport.post;

//도로 제보글 리스트뷰의 아이템 데이터
public class RoadListItem {
    private String postNum,writerID, availability,type,angle,stairs,breakage;
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
    public void setAngle(String tv3){
        angle = tv3;
    }
    public void setStairs(String tv4){
        stairs = tv4;
    }
    public void setBreakage(String tv5){
        breakage = tv5;
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
    public String getAngle(){ return this.angle; }
    public String getStairs(){
        return this.stairs;
    }
    public String getBreakage(){
        return this.breakage;
    }
}
