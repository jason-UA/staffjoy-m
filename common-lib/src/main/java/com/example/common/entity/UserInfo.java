package com.example.common.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class UserInfo {

    public static UserInfo makeUserinfo(Map<String, ?> map) {
        UserInfo userInfo = new UserInfo();
        userInfo.userName = (String)map.get("user_name");
        userInfo.scope = (ArrayList<String>)map.get("scope");
        userInfo.exp = (Long)map.get("exp");
        userInfo.userID = String.valueOf(map.get("user_id"));
        userInfo.authorities = (ArrayList<String>)map.get("authorities");
        userInfo.jti = (String)map.get("jti");
        userInfo.clientID = (String)map.get("client_id");
        return userInfo;
    }

    private String userName;

    private ArrayList<String> scope;

    private long exp;

    private String userID;

    private  ArrayList<String> authorities;

    private String jti;

    private String clientID;

}

