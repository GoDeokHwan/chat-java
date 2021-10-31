package io.chat.java.api.external.home;

import io.chat.java.api.support.ApiResult;
import io.chat.java.api.support.ApiStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/info")
    public ApiResult processOpen() {
        return ApiResult.of(ApiStatus.SUCCESS);
    }

}
