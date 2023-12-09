package com.fmi.demo.exposition.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionEnum implements ErrorDispatcher {
    EMPTY_FIELD("Some of the fields were incomplete","error_empty_field"),
    INVALID_FIELD("One or more fields are invalid", "error_invalid_id"),
    OBJECT_NOT_FOUND("The object couldn't be found", "error_object_not_found"),
    MISSING_USER("THE user is missing","error_missing_user"),
    AUTHORIZATION_FAIL("You don't have controll on this resource","error_auth_fail"),
    FAILED_CLASIFICATION("The Clasification has failed","error_failed_classification"),
    SAVE_FAILED_EXCEPITON("The save has failed","error_failed_save");
    ExceptionEnum(String error_message, String error_key) {
        this.errorMessage = error_message;
        this.errorKey = error_key;
    }

    private String errorMessage;
    private String errorKey;
}
