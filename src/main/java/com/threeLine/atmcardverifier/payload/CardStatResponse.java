package com.threeLine.atmcardverifier.payload;

import lombok.Data;

import java.util.Map;

@Data
public class CardStatResponse {

    private boolean success;
    private int start;
    private int limit;
    private int size;
    private Map<String, Object> payload;
}
