package com.campraca.twitterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProfileInfoResponse extends GeneralResponse {

    private final ProfileInfo profileInfo;
    private final List<TweetInfo> tweetList;
}
