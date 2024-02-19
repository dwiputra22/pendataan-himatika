package com.pendataan.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationCode {
    SUCCESS("00", "SUCCESS"),
    VALIDATION_ERROR("01", "Validation error"),
    INVALID_PARAMETER("02", "Invalid parameter"),
    WORKSHOP_ALREADY_EXISTS("03", "Workshop already exists"),
    WORKSHOP_NOT_FOUND("04", "Workshop is not found"),
    PROPOSAL_NOT_FOUND("05", "Proposal is not found"),
    CANNOT_DELETE_WORKSHOP("06", "Cannot delete workshop");

    private final String code;
    private final String message;
}
