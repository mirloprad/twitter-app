package com.campraca.twitterapp.adapter.database;

import com.campraca.twitterapp.dto.ProfileInfo;
import com.campraca.twitterapp.dto.UpdatePortfolioRequest;
import com.campraca.twitterapp.entity.PortfolioEntity;
import com.campraca.twitterapp.repository.PortfolioRepository;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
public class ProfileDataAdapter {

    private final PortfolioRepository portfolioRepository;

    public Try<List<ProfileInfo>> getAllPortfolios() {
        return Try.of(() -> StreamSupport.stream(portfolioRepository.findAll().spliterator(), false)
                .map(this::mapPortfolio)
                .collect(Collectors.toList()))
                .onFailure(e -> log.error("Error getting all portfolios, {}", e.getMessage()));
    }

    public Try<ProfileInfo> getPortfolioById(Integer idPortfolio) {
        return Option.ofOptional(portfolioRepository.findById(idPortfolio)
                .map(this::mapPortfolio))
                .toTry()
                .onFailure(e -> log.error("Error getting portfolio for user {}, {}", idPortfolio, e.getMessage()));
    }

    public Try<Boolean> updatePortfolio(Integer idPortfolio, UpdatePortfolioRequest request) {
        return Option.ofOptional(portfolioRepository.findById(idPortfolio))
                .map(entity -> mapUpdatedEntity(entity, request))
                .toTry()
                .mapTry(portfolioRepository::save)
                .map(portfolioEntity -> true)
                .onFailure(e -> log.error("Error saving info for user {} ,{}", idPortfolio, e.getMessage()));
    }

    private PortfolioEntity mapUpdatedEntity(PortfolioEntity entity, UpdatePortfolioRequest request) {
        entity.setDescription(request.getDescription());
        entity.setImageUrl(request.getImageUrl());
        entity.setTitle(request.getTitle());
        entity.setTwitterUserName(request.getTwitterUserName());
        entity.setTittle(request.getTittle());
        return entity;
    }

    private ProfileInfo mapPortfolio(PortfolioEntity portfolio) {
        return ProfileInfo.builder()
                .description(portfolio.getDescription())
                .idPortfolio(portfolio.getIdPortfolio())
                .imageUrl(portfolio.getImageUrl())
                .title(portfolio.getTitle())
                .tittle(portfolio.getTittle())
                .twitterUserName(portfolio.getTwitterUserName())
                .build();
    }
}
