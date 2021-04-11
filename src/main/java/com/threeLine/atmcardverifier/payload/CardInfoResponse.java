package com.threeLine.atmcardverifier.payload;

import lombok.Data;

@Data
public class CardInfoResponse {

    private boolean success;
    private InfoPayload payload;
}
