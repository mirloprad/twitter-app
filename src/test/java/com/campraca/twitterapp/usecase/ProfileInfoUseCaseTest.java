package com.campraca.twitterapp.usecase;

import com.campraca.twitterapp.adapter.database.ProfileDataAdapter;
import com.campraca.twitterapp.adapter.twitter.TwitterAdapter;
import com.campraca.twitterapp.dto.ProfileInfo;
import com.campraca.twitterapp.dto.ProfileInfoResponse;
import com.campraca.twitterapp.dto.TweetInfo;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProfileInfoUseCaseTest {

    @Mock
    private ProfileDataAdapter profileDataAdapter;
    @Mock
    private TwitterAdapter twitterAdapter;
    private ProfileInfoUseCase profileInfoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        profileInfoUseCase = new ProfileInfoUseCase(profileDataAdapter, twitterAdapter);
    }

    @Test
    void shouldGetAllUsers() {
        ProfileInfo profileInfo = ProfileInfo.builder().build();
        TweetInfo tweetInfo = TweetInfo.builder().build();

        when(profileDataAdapter.getAllPortfolios()).thenReturn(Try.of(() -> List.of(profileInfo)));
        when(twitterAdapter.getTwitterTimeLineByUser(anyString())).thenReturn(Try.of(() -> List.of(tweetInfo)));

        Try<List<ProfileInfoResponse>> profiles = profileInfoUseCase.getUsers();

        assertTrue(profiles.isSuccess());

        List<ProfileInfoResponse> profileInfoResponseList = profiles.get();
        assertEquals(1, profileInfoResponseList.size());
        assertNotNull(profileInfoResponseList.get(0));
        assertNotNull(profileInfoResponseList.get(0).getProfileInfo());
        assertEquals(profileInfo, profileInfoResponseList.get(0).getProfileInfo());
    }

    @Test
    void shouldGetUserInfoById() {
        ProfileInfo profileInfo = ProfileInfo.builder().twitterUserName("TWEET_USER_NAME_TEST").build();
        TweetInfo tweetInfo = TweetInfo.builder().build();

        when(profileDataAdapter.getPortfolioById(1)).thenReturn(Try.of(() -> profileInfo));
        when(twitterAdapter.getTwitterTimeLineByUser("TWEET_USER_NAME_TEST")).thenReturn(Try.of(() -> List.of(tweetInfo)));

        Try<ProfileInfoResponse> profile = profileInfoUseCase.getProfileInfoById(1);

        assertTrue(profile.isSuccess());
        assertNotNull(profile.get());
        assertEquals(profileInfo, profile.get().getProfileInfo());
        assertNotNull(profile.get().getTweetList());
        assertEquals(tweetInfo, profile.get().getTweetList().get(0));
    }
}