package com.campraca.twitterapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdatePortfolioRequest {
    @NotBlank(message = "description null or empty")
    private String description;
    @NotBlank(message = "imageUrl null or empty")
    private String imageUrl;
    @NotBlank(message = "twitterUserName null or empty")
    private String twitterUserName;
    @NotBlank(message = "title null or empty")
    private String title;
    @NotBlank(message = "tittle null or empty")
    private String tittle;
}
