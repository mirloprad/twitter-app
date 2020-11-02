package com.campraca.twitterapp.usecase;

import com.campraca.twitterapp.adapter.database.ProfileDataAdapter;
import com.campraca.twitterapp.adapter.twitter.TwitterAdapter;
import com.campraca.twitterapp.dto.ProfileInfo;
import com.campraca.twitterapp.dto.ProfileInfoResponse;
import com.campraca.twitterapp.dto.UpdatePortfolioRequest;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ProfileInfoUseCase {

    private final ProfileDataAdapter profileDataAdapter;
    private final TwitterAdapter twitterAdapter;

    public Try<List<ProfileInfoResponse>> getUsers() {
        return profileDataAdapter.getAllPortfolios()
                .map(resp -> resp.stream().map(this::mapProfileInfo)
                        .collect(Collectors.toList()))
                .onFailure(e -> log.error("Error getting users info {}", e.getMessage()));
    }

    public Try<ProfileInfoResponse> getProfileInfoById(Integer idProfile) {
        return profileDataAdapter.getPortfolioById(idProfile)
                .map(this::mapProfileInfo)
                .flatMap(this::mapTwitterInfo)
                .onFailure(e -> log.error("Error getting portfolio by idPortfolio {}, {}", idProfile, e.getMessage()));
    }

    private Try<ProfileInfoResponse> mapTwitterInfo(ProfileInfoResponse resp) {
        return twitterAdapter.getTwitterTimeLineByUser(resp.getProfileInfo().getTwitterUserName())
                .map(timeLine -> new ProfileInfoResponse(resp.getProfileInfo(), timeLine))
                .onFailure(e -> log.error("Error getting twitter info for user {}, {}",
                        resp.getProfileInfo().getTwitterUserName(), e.getMessage()));
    }

    private ProfileInfoResponse mapProfileInfo(ProfileInfo profile) {
        return new ProfileInfoResponse(profile, null);
    }

    public Try<Boolean> updatePortfolio(Integer idProfile, UpdatePortfolioRequest request) {
        return profileDataAdapter.updatePortfolio(idProfile, request)
                .onFailure(e -> log.error("Error saving user info {} ,{}", idProfile, e.getMessage()));
    }
}
