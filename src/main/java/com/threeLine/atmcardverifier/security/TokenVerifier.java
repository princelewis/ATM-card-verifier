package com.threeLine.atmcardverifier.security;

import com.threeLine.atmcardverifier.util.TokenHasher;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
@Data
public class TokenVerifier {


    public boolean verifyToken(String token, String appKey, String timeStamp) {

        //Check if the parameters are empty

        if (!StringUtils.hasText(token)) {
            log.info("Checking if token is null");
            throw new BadCredentialsException("The API token was not found.");

        } else if (!StringUtils.hasText(appKey)){
            log.info("Checking if appKey is null");
            throw new BadCredentialsException("The API appKey was not found.");

        } else if (!StringUtils.hasText(timeStamp)){
            log.info("Checking if timeStamp is null");
            throw new BadCredentialsException("The API timeStamp was not found.");
        }

        //Check if the hashed string equals the stored one generated from hasher
            String systemHashedToken = TokenHasher.getHashedToken(appKey,timeStamp);


            if (token.equals(systemHashedToken)) return true;

        log.info("Api token is wrong");
//            throw new BadCredentialsException("The API token is wrong.");
        return false;

    }

}
