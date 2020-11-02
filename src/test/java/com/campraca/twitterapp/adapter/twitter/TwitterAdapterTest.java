package com.campraca.twitterapp.adapter.twitter;

import com.campraca.twitterapp.dto.TweetInfo;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TwitterAdapterTest {

    @Mock
    private Twitter twitter;
    @Mock
    private TimelineOperations timelineOperations;
    private TwitterAdapter twitterAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        twitterAdapter = new TwitterAdapter(twitter);
    }

    @Test
    void shouldGetTwitterTimeLineByUser() {
        when(twitter.timelineOperations()).thenReturn(timelineOperations);
        when(timelineOperations.getUserTimeline("TWITTER_USER_NAME_TEST")).thenReturn(List.of(buildTweet()));

        Try<List<TweetInfo>> tweetList = twitterAdapter.getTwitterTimeLineByUser("TWITTER_USER_NAME_TEST");

        assertTrue(tweetList.isSuccess());
        List<TweetInfo> tweetInfoList = tweetList.get();
        TweetInfo tweetInfo = tweetInfoList.get(0);
        assertEquals(1L, tweetInfo.getId());
        assertEquals("TWEET_TEST", tweetInfo.getText());
        assertNotNull(tweetInfo.getCreatedAt());
        assertEquals("FROM_USER_TEST", tweetInfo.getFromUser());
        assertEquals("PROFILE_IMAGE_URL", tweetInfo.getProfileImageUrl());
        assertEquals(19L, tweetInfo.getToUserId());
        assertEquals(45L, tweetInfo.getFromUserId());
        assertEquals("ES", tweetInfo.getLanguageCode());
        assertEquals("SOURCE_TEST", tweetInfo.getSource());
    }

    private Tweet buildTweet() {
        return new Tweet(1L, "TWEET_TEST", new Date(), "FROM_USER_TEST", "PROFILE_IMAGE_URL",
                19L, 45L, "ES", "SOURCE_TEST");
    }
}