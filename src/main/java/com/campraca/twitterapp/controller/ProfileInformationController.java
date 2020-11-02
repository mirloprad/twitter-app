package com.campraca.twitterapp.controller;

import com.campraca.twitterapp.dto.ErrorResponse;
import com.campraca.twitterapp.dto.GeneralResponse;
import com.campraca.twitterapp.dto.UpdatePortfolioRequest;
import com.campraca.twitterapp.dto.UpdatePortfolioResponse;
import com.campraca.twitterapp.dto.UsersInfoResponse;
import com.campraca.twitterapp.usecase.ProfileInfoUseCase;
import com.campraca.twitterapp.util.ResponseUtil;
import io.vavr.collection.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ProfileInformationController {

    private final ProfileInfoUseCase profileInfoUseCase;

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllUsers() {
        return profileInfoUseCase.getUsers()
                .map(resp -> ResponseUtil.buildOkResponse(new UsersInfoResponse(resp)))
                .getOrElse(ResponseUtil.buildError(new ErrorResponse("Error getting users.", "500"),
                        INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{idProfile}")
    public ResponseEntity<GeneralResponse> getProfileInfo(@PathVariable("idProfile") Integer idProfile) {
        return profileInfoUseCase.getProfileInfoById(idProfile)
                .map(ResponseUtil::buildOkResponse)
                .getOrElse(ResponseUtil.buildError(new ErrorResponse("Not found profile.", "404"),
                        BAD_GATEWAY));
    }

    @PutMapping("/{idProfile}")
    public ResponseEntity<GeneralResponse> modifyUserInfo(@PathVariable("idProfile") Integer idProfile,
                                                          @Valid @RequestBody UpdatePortfolioRequest request) {
        return profileInfoUseCase.updatePortfolio(idProfile, request)
                .map(resp -> ResponseUtil.buildOkResponse(new UpdatePortfolioResponse(resp)))
                .getOrElse(ResponseUtil.buildError(new ErrorResponse("Not found profile.", "404"),
                        BAD_GATEWAY));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Iterator.ofAll(ex.getBindingResult().getAllErrors())
                .peek(error -> log.error("Validation error: {}", error.getDefaultMessage()))
                .map(error -> new ErrorResponse("400", "Error in field " + error.getDefaultMessage()))
                .getOrElse(new ErrorResponse("500", "Unknown validation error."));
    }
}
