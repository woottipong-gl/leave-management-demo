package com.woottipong.leavemanagementdemo.service;

import com.woottipong.leavemanagementdemo.exception.LoginException;
import com.woottipong.leavemanagementdemo.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Base64;

@Service
public class UserManagementService {

    private final HttpServletRequest httpRequest;

    public UserManagementService(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public UserModel getUserLogon() {
        String authorization = httpRequest.getHeader("Authorization");
        if(!StringUtils.hasText(authorization)) {
            throw new LoginException("Authorization not found!");
        }
        try {
            String credentialBase64 = authorization.substring(6);
            String credential = new String(Base64.getDecoder().decode(credentialBase64));
            String userId = credential.split(":")[0];
            String role = credential.split(":")[1];
            if(!StringUtils.hasText(userId) || !StringUtils.hasText(role)) {
                throw new Exception();
            }
            return new UserModel(userId, Arrays.asList(role));
        } catch (Exception ex) {
            throw new LoginException("Authorization incorrect!");
        }
    }
}
