package com.threeLine.atmcardverifier.service;

import com.threeLine.atmcardverifier.payload.CardStatResponse;

public interface CardStatService {

    CardStatResponse getCardStat(int start, int limit);
}
