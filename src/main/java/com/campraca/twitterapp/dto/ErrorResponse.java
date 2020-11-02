package com.campraca.twitterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse extends GeneralResponse {
    private final String code;
    private final String failure;
}
