package com.campraca.twitterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePortfolioResponse extends GeneralResponse {
    private final boolean success;
}
