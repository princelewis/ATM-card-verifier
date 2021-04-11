package com.threeLine.atmcardverifier.service;

import com.threeLine.atmcardverifier.payload.CardInfoResponse;

public interface CardInfoService {

    CardInfoResponse getCardInfo(String cardNum);
}
