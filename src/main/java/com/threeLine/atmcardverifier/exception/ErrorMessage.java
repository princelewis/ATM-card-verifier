package com.threeLine.atmcardverifier.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private Date date;
    private String message;
    private String details;

    public ErrorMessage(Date date, String message, String details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }



}
