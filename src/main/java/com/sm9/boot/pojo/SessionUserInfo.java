package com.sm9.boot.pojo;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SessionUserInfo {
    private int userId;
    private String username;
    private String userUnit;
    private String userEmail;
    private String userPhoneNumber;
    private List<String> roleIds;
    private Set<String> menuList;
    private List<String> permissionList;
}
