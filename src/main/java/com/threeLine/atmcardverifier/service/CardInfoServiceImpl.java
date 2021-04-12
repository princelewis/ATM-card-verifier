package com.threeLine.atmcardverifier.service;

import com.threeLine.atmcardverifier.exception.CardInfoServiceException;
import com.threeLine.atmcardverifier.exception.ErrorMessages;
import com.threeLine.atmcardverifier.model.CardInfo;
import com.threeLine.atmcardverifier.payload.CardInfoResponse;
import com.threeLine.atmcardverifier.payload.InfoPayload;
import com.threeLine.atmcardverifier.repository.CardInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Service("cardInfoService")
@Slf4j
public class CardInfoServiceImpl implements CardInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CardInfoRepository cardInfoRepository;

    @Value("${binlist.url}")
    private String url;

    @Override
    public CardInfoResponse getCardInfo(String cardNum) {

        //Check if the supplied PAN number is at least 6 in number
        if (cardNum.length() < 6) {
            throw new CardInfoServiceException(ErrorMessages.INCOMPLETE_CARD_DIGIT.getErrorMessage());
        }

        //Cut out the first 6 digits of the PAN
        String newCardNumber = cutOutFirstSixChar(cardNum);

        JSONObject thirdPartyApiResponse;
        try {
            log.info("Trying to fetch data from third-party API ---");

            ResponseEntity<String> responseEntity = restTemplate
                    .postForEntity(url + newCardNumber,null,String.class);

            log.info("Successfully made a call to ***  {}",url + newCardNumber);

            thirdPartyApiResponse = new JSONObject(responseEntity.getBody());


        } catch (Exception ex) {
            //If an error occurred while making a request, persist data
            CardInfo cardInfo = new CardInfo();
            cardInfo.setSuccess(false);
            cardInfo.setCardNumber(newCardNumber);
            cardInfoRepository.save(cardInfo);

            if (ex instanceof HttpStatusCodeException) {
                HttpStatusCodeException exception = (HttpStatusCodeException) ex;
                log.error("Http exception occurred while trying to get response from third-party API" +
                                "with code: {} and body: {}", exception.getStatusCode(),
                        exception.getResponseBodyAsString(), exception);

                throw new CardInfoServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
            }

            throw new CardInfoServiceException(ErrorMessages.CONNECTIVITY_ERROR.getErrorMessage());
        }

        //Check that the response from the third-party API is not null
        if(thirdPartyApiResponse.isEmpty()){
            throw new CardInfoServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        //Get the desired values from the third-party api response payload
        String scheme = thirdPartyApiResponse.getString("scheme");
        String type = thirdPartyApiResponse.getString("type");

        //Since the "bank" obk=ject in the api response can be null
        String bankName = (thirdPartyApiResponse.getJSONObject("bank").isEmpty()) ? null
                : thirdPartyApiResponse.getJSONObject("bank").getString("name");

        //Build the card_info table for persistence
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(newCardNumber);
        cardInfo.setSuccess(true);
        cardInfo.setCardScheme(scheme);
        cardInfo.setCardType(type);
        cardInfo.setBank(bankName);

        //Store insert the card_info data in the DB
        cardInfoRepository.save(cardInfo);

        log.info("Successfully stored response to DB");

        //Build The app's response payload
        InfoPayload payload = new InfoPayload(scheme, type, bankName);
        CardInfoResponse cardInfoResponse = new CardInfoResponse();
        cardInfoResponse.setSuccess(true);
        cardInfoResponse.setPayload(payload);



        return cardInfoResponse;
    }

    private String cutOutFirstSixChar(String cardNum){
        return cardNum.substring(0,6);
    }
}
