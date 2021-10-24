package io.chat.java.api.support;

import lombok.Getter;

@Getter
public enum ApiStatus implements ApiStatusResponsible {
    SUCCESS(0, "성공", true),
    ERROR(-500, "관리자에게 문의하세요.", false),
    REQUEST_METHOD_NOT_SUPPORTED(-405, "지원하지 않는 요청입니다.", false),
    BAD_REQUEST_ERROR(-400, "파라미터 형식이 잘못되었습니다.", false),
    MISSING_REQUEST_PARAMETER(-411, "필수 정보가 잘못되었습니다.", false),

    // 인증
    AUTHENTICATION(-1, "아이디나 암호가 틀렸습니다", true),
    AUTHENTICATION_FAILED(-2, "인증 실패", false)
    , JWT_TOKEN_EXPIRED(-3, "접속한지 오래되어 재로그인이 필요합니다", false)
    , WAITING_APPROVAL_AGENT(-4, "승인 대기중인 상담원입니다", false)
    , DUPLICATION_ACCESS_EXCEPTION(-5, "중복 로그인은 불가능합니다.", false)
    , DUPLICATION_LOGIN_EXCEPTION(-6, "다른곳에서 로그인 되었습니다.", false)
    , IS_NOT_LOGIN_PAGE(-7, "로그인 페이지를 통한 로그인이 아닙니다", false)
    , IS_NOT_ADMIN_LOGIN(-8, "상담원을 찾을 수 없습니다", false)
    , IS_NOT_AGENT_LOGIN(-9, "상담원을 찾을 수 없습니다", false)
    , BRAND_IS_NOT_SHOW(-10, "존재하지 않는 브랜드입니다", true)
    , NOT_AUTHORIZED(-11, "아직 역할이 부여되지 않았습니다. 최고 관리자에게 역할 부여를 확인 후 다시 시도해주세요.", false),
    ;

    private Integer code;
    private String message;
    private boolean isShort;

    ApiStatus(Integer code, String message, boolean isShort) {
        this.code = code;
        this.message = message;
        this.isShort = isShort;

    }

}
