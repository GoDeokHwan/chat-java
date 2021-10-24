package io.chat.java.api.support;

import lombok.Getter;

public class ApiException extends RuntimeException{
    @Getter
    private ApiStatusResponsible apiStatus;

    public ApiException(ApiStatusResponsible apiStatus) {
        super(apiStatus.getMessage());
        this.apiStatus = apiStatus;
    }

    public ApiException(String apiStatus) {
        super(apiStatus);
    }
}
