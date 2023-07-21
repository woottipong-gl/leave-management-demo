package com.woottipong.leavemanagementdemo.model;

import java.util.List;

public record UserModel (String userId, List<String> roles) {}
