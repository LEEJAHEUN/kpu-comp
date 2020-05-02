/*
로그인 요청 시 보낼 데이터
*/
package com.example.mobilitySupport.data;

public class LoginData {
    String userID;
    String userPW;

    public LoginData(String userID, String userPW){
       this.userID = userID;
       this.userPW = userPW;
    }
}
