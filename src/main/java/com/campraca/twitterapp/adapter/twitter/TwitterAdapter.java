package com.campraca.twitterapp.adapter.twitter;

import com.campraca.twitterapp.dto.TweetInfo;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TwitterAdapter {

    private final Twitter twitter;

    public Try<List<TweetInfo>> getTwitterTimeLineByUser(String twitterUserName) {
        return Try.of(() -> twitter.timelineOperations().getUserTimeline(twitterUserName))
                .map(list -> list.stream()
                        .map(this::mapTweet)
                        .collect(Collectors.toList()))
                .onFailure(e -> log.error("Error getting twitter info for user{}, {}", twitterUserName, e.getMessage()));
    }

    private TweetInfo mapTweet(Tweet tweet) {
        return TweetInfo.builder()
                .createdAt(tweet.getCreatedAt())
                .fromUser(tweet.getFromUser())
                .fromUserId(tweet.getFromUserId())
                .id(tweet.getId())
                .idStr(tweet.getIdStr())
                .inReplyToScreenName(tweet.getInReplyToScreenName())
                .inReplyToStatusId(tweet.getInReplyToStatusId())
                .inReplyToUserId(tweet.getInReplyToUserId())
                .languageCode(tweet.getLanguageCode())
                .profileImageUrl(tweet.getProfileImageUrl())
                .retweetCount(tweet.getRetweetCount())
                .source(tweet.getSource())
                .text(tweet.getText())
                .toUserId(tweet.getToUserId())
                .build();
    }
}
