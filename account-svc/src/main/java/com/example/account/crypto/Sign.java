package com.example.account.crypto;

import com.example.common.env.EnvConfig;
import org.hibernate.service.spi.ServiceException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Sign {
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_SUPPORT = "support";


    public static String generateEmailConfirmationToken(Long userId, String email, String signingToken) {
        return String.valueOf(userId) + "$" + email + "$" + signingToken;
    }

    public static Map<String, String> verifyEmailConfirmationToken(String tokenString, String signingToken) {
        Map<String, String> map = new HashMap<String, String>();
        String[] pieces =  tokenString.split("#");
        if (pieces.length != 3) {
            throw new ServiceException("incorrent token");
        }
        map.put("user_id", pieces[0]);
        map.put("email", pieces[1]);
        return map;
    }

}
