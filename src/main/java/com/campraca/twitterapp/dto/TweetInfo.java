package com.campraca.twitterapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetInfo {

    private final long id;
    private final String idStr;
    private final String text;
    private final Date createdAt;
    private final String fromUser;
    private final String profileImageUrl;
    private final Long toUserId;
    private final Long inReplyToStatusId;
    private final Long inReplyToUserId;
    private final String inReplyToScreenName;
    private final long fromUserId;
    private final String languageCode;
    private final String source;
    private final Integer retweetCount;
    private final boolean retweeted;
}
