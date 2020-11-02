package com.campraca.twitterapp.adapter.database;

import com.campraca.twitterapp.dto.ProfileInfo;
import com.campraca.twitterapp.entity.PortfolioEntity;
import com.campraca.twitterapp.repository.PortfolioRepository;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProfileDataAdapterTest {

    @Mock
    private PortfolioRepository portfolioRepository;
    private ProfileDataAdapter profileDataAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        profileDataAdapter = new ProfileDataAdapter(portfolioRepository);
    }

    @Test
    public void shouldGetAllUsers() {
        List<PortfolioEntity> entity = List.of(buildEntity());
        when(portfolioRepository.findAll()).thenReturn(entity);

        Try<List<ProfileInfo>> portfolios = profileDataAdapter.getAllPortfolios();

        assertTrue(portfolios.isSuccess());
        List<ProfileInfo> list = portfolios.get();
        assertEquals(1, list.size());

        ProfileInfo profileInfo = list.get(0);
        assertEquals("DESCRIPTION_TEST", profileInfo.getDescription());
        assertEquals("IMAGE_URL_TEST", profileInfo.getImageUrl());
        assertEquals("TITLE_TEST", profileInfo.getTitle());
        assertEquals("TITTLE_TEST", profileInfo.getTittle());
        assertEquals("TWITTER_USER_NAME_TEST", profileInfo.getTwitterUserName());
    }

    @Test
    public void shouldGetUserById() {
        PortfolioEntity entity = buildEntity();
        when(portfolioRepository.findById(1)).thenReturn(Optional.of(entity));

        Try<ProfileInfo> portfolio = profileDataAdapter.getPortfolioById(1);

        assertTrue(portfolio.isSuccess());

        ProfileInfo profileInfo = portfolio.get();
        assertEquals("DESCRIPTION_TEST", profileInfo.getDescription());
        assertEquals("IMAGE_URL_TEST", profileInfo.getImageUrl());
        assertEquals("TITLE_TEST", profileInfo.getTitle());
        assertEquals("TITTLE_TEST", profileInfo.getTittle());
        assertEquals("TWITTER_USER_NAME_TEST", profileInfo.getTwitterUserName());
    }

    private PortfolioEntity buildEntity() {
        PortfolioEntity entity = new PortfolioEntity();
        entity.setTittle("TITTLE_TEST");
        entity.setTwitterUserName("TWITTER_USER_NAME_TEST");
        entity.setTitle("TITLE_TEST");
        entity.setImageUrl("IMAGE_URL_TEST");
        entity.setDescription("DESCRIPTION_TEST");
        entity.setIdPortfolio(1);
        return entity;
    }
}