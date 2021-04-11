package com.threeLine.atmcardverifier.payload;

import lombok.Data;

@Data
public class InfoPayload {

    private String scheme;
    private String type;
    private String bank;

    public InfoPayload(String scheme, String type, String bank) {
        this.scheme = scheme;
        this.type = type;
        this.bank = bank;
    }
}
