package com.campraca.twitterapp;

import com.campraca.twitterapp.adapter.database.ProfileDataAdapter;
import com.campraca.twitterapp.adapter.twitter.TwitterAdapter;
import com.campraca.twitterapp.dto.ProfileInfo;
import com.campraca.twitterapp.dto.TweetInfo;
import com.campraca.twitterapp.dto.UpdatePortfolioRequest;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class TwitterAppIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileDataAdapter profileDataAdapter;

    @MockBean
    private TwitterAdapter twitterAdapter;

    @Test
    void shouldReturnAllUsers() throws Exception {
        when(profileDataAdapter.getAllPortfolios()).thenReturn(Try.of(() -> List.of(buildProfile())));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"users\":[{\"profileInfo\":{\"idPortfolio\":1," +
                        "\"twitterUserName\":\"TWITTER_USER_NAME\"},\"tweetList\":null}]}"));
    }

    @Test
    void shouldReturnProfileInfo() throws Exception {
        when(profileDataAdapter.getPortfolioById(1)).thenReturn(Try.of(this::buildProfile));
        when(twitterAdapter.getTwitterTimeLineByUser("TWITTER_USER_NAME"))
                .thenReturn(Try.of(() -> List.of(buildTweet())));

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"profileInfo\":{\"idPortfolio\":1,\"twitterUserName\":\"TWITTER_USER_NAME\"}," +
                        "\"tweetList\":[{\"id\":0,\"text\":\"TWEET_TEXT_TEST\",\"fromUserId\":0,\"retweeted\":false}]}"));
    }

    @Test
    void shouldUpdatePortfolio() throws Exception {
        when(profileDataAdapter.getPortfolioById(1)).thenReturn(Try.of(this::buildProfile));
        when(profileDataAdapter.updatePortfolio(eq(1), any(UpdatePortfolioRequest.class)))
                .thenReturn(Try.of(() -> true));

        mockMvc.perform(put("/api/user/1")
                .contentType("application/json")
                .content("{\"description\":\"DESCRIPTION_TEST\",\"imageUrl\":\"IMAGE_URL_TEST\"," +
                        "\"twitterUserName\":\"TWITTER_USER_NAME_TEST\",\"title\":\"TITLE_TEST\",\"tittle\":\"TITTLE_TEST\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"success\":true}"));
    }

    private TweetInfo buildTweet() {
        return TweetInfo.builder()
                .text("TWEET_TEXT_TEST")
                .build();
    }

    private ProfileInfo buildProfile() {
        return ProfileInfo.builder().idPortfolio(1)
                .twitterUserName("TWITTER_USER_NAME")
                .build();
    }
}
