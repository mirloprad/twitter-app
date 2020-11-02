package com.campraca.twitterapp.config;

import com.campraca.twitterapp.adapter.database.ProfileDataAdapter;
import com.campraca.twitterapp.adapter.twitter.TwitterAdapter;
import com.campraca.twitterapp.repository.PortfolioRepository;
import com.campraca.twitterapp.usecase.ProfileInfoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.social.twitter.api.Twitter;

@Configuration
@Import(TwitterConfig.class)
public class ProfileInfoConfig {

    @Bean
    public TwitterAdapter twitterAdapter(Twitter twitter) {
        return new TwitterAdapter(twitter);
    }

    @Bean
    public ProfileDataAdapter profileDataAdapter(PortfolioRepository portfolioRepository) {
        return new ProfileDataAdapter(portfolioRepository);
    }

    @Bean
    public ProfileInfoUseCase profileInfoUseCase(ProfileDataAdapter profileDataAdapter,
                                                 TwitterAdapter twitterAdapter) {
        return new ProfileInfoUseCase(profileDataAdapter, twitterAdapter);
    }
}
