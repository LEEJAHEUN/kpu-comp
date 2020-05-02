package com.example.mobilitySupport.data;

public class JoinData {
    private String userID;
    private String userPW;
    private String userEmail;
    private String userType;

    public JoinData(String userID, String userPW, String userEmail, String userType){
        this.userID = userID;
        this.userPW = userPW;
        this.userEmail = userEmail;
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPW() {
        return userPW;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserType() {
        return userType;
    }
}
