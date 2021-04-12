package com.threeLine.atmcardverifier.service;

import com.threeLine.atmcardverifier.exception.CardInfoServiceException;
import com.threeLine.atmcardverifier.exception.ErrorMessages;
import com.threeLine.atmcardverifier.model.CardInfo;
import com.threeLine.atmcardverifier.model.CardNumAndCount;
import com.threeLine.atmcardverifier.payload.CardStatResponse;
import com.threeLine.atmcardverifier.repository.CardInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("cardStatService")
@Slf4j
public class CardStatServiceImpl implements CardStatService{

    @Autowired
    CardInfoRepository cardInfoRepository;

    @Override
    public CardStatResponse getCardStat(int start, int limit) {

        validatePageNumberAndSize(start, limit);

        List<CardInfo> allRequestedCards = cardInfoRepository.findAll();
        log.info("Fetched all the records in the DB");

        int sizeOfRequestedCards = allRequestedCards.size();

        //Get the page result for the start and limit points
        Pageable pageable =  PageRequest.of(start, limit);

        Page<CardNumAndCount> sortedCardNumbers = cardInfoRepository.getSortedCardCount(pageable);

        System.out.println(sortedCardNumbers.toList());
        log.info("Fetched all the sorted card info from the DB");

        //If no record is found, throw error
        if (sortedCardNumbers.isEmpty()) {
            throw new CardInfoServiceException(ErrorMessages.EMPTY_LIST.getErrorMessage());
        }

        Map<String, Object> statPayload = new LinkedHashMap<>();
        sortedCardNumbers.toList().forEach(p -> statPayload.put(p.getCardNum(), p.getCount()));

        CardStatResponse cardStatResponse = new CardStatResponse();

        //Build the response object
        cardStatResponse.setSuccess(true);
        cardStatResponse.setLimit(limit);
        cardStatResponse.setStart(start);
        cardStatResponse.setSize(sizeOfRequestedCards);
        cardStatResponse.setPayload(statPayload);

        return cardStatResponse;
    }


    private void validatePageNumberAndSize(int start, int limit) {
        if (start < 0) {
            throw new CardInfoServiceException(ErrorMessages.OUT_OF_BOUNDS.getErrorMessage());
        }
    }
}
