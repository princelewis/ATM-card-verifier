package com.threeLine.atmcardverifier.model;

import lombok.Data;

@Data
public class CardNumAndCount {

    private String cardNum;
    private long count;

    public CardNumAndCount(String cardNum, long count) {
        this.cardNum = cardNum;
        this.count = count;
    }
}
