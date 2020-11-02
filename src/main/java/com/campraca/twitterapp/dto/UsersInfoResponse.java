package com.campraca.twitterapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UsersInfoResponse extends GeneralResponse {

    private final List<ProfileInfoResponse> users;
}
