package com.threeLine.atmcardverifier.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;

import java.security.MessageDigest;

@Slf4j
public class TokenHasher {

    public static String getHashedToken(String appKey, String timeStamp) {

        String password = appKey + timeStamp;
        String dataString = "";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(password.getBytes());

            byte[] dataEncrypt = md.digest();


            dataString = new String(Base64.encode(dataEncrypt));

        } catch (Exception ex) {
            log.error("An error occurred while hashing token with error message as ****  {}", ex.getMessage(), ex);

        }

        return dataString;

    }


}
