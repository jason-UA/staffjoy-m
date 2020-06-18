package com.example.common.auth;

import com.example.common.entity.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class AuthContext {

    private static String getRequetHeader(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            String value = request.getHeader(headerName);
            return value;
        }
        return null;
    }

    private static UserInfo getAuthorizationUserInfo() {
        if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details.getDecodedDetails() instanceof UserInfo) {
                return (UserInfo) details.getDecodedDetails();
            }
        }
        return null;
    }

    public static String getUserId() {
        UserInfo userInfo = getAuthorizationUserInfo();
        if (userInfo != null) {
            return userInfo.getUserID();
        }
        return null;
    }

//    public static String getUserId() {
//        return getRequetHeader(AuthConstant.CURRENT_USER_HEADER);
//    }
//
//    public static String getAuthz() {
//        return getRequetHeader(AuthConstant.AUTHORIZATION_HEADER);
//    }

}