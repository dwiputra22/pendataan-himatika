package com.pendataan.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseStatus {
    private String responseCode;
    private String responseMessage;

    public ResponseStatus(ApplicationCode applicationCode) {
        this.responseCode = applicationCode.getCode();
        this.responseMessage = applicationCode.getMessage();
    }
}
