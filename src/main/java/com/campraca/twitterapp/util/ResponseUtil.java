package com.campraca.twitterapp.util;

import com.campraca.twitterapp.dto.GeneralResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtil {

    public static <T extends GeneralResponse> ResponseEntity<GeneralResponse> buildOkResponse(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T extends GeneralResponse> ResponseEntity<GeneralResponse> buildError(T bodyError, HttpStatus status) {
        return ResponseEntity.status(status).body(bodyError);
    }
}
