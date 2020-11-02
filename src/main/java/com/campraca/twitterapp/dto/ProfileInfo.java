package com.campraca.twitterapp.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileInfo {
    private final Integer idPortfolio;
    private final String description;
    private final String imageUrl;
    private final String twitterUserName;
    private final String title;
    private final String tittle;
}