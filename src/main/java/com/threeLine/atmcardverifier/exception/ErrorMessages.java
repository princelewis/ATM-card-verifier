package com.threeLine.atmcardverifier.exception;


public enum ErrorMessages {
    EMPTY_LIST("input record was not found, list is empty"),
    NO_RECORD_FOUND("the input record was not found, card may not be registered "),
    OUT_OF_BOUNDS("the input range was not found. out of range"),
    CONNECTIVITY_ERROR("An error occurred while connecting to the third-party API"),
    INCOMPLETE_CARD_DIGIT("number of card digit was less than threshold ");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
