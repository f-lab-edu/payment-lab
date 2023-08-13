package org.collaborator.paymentlab.common.error

enum class ErrorCode(
    val msg: String,
    val status: Int
) {
    // service error
    INVALID_INPUT("잘못된 값을 입력하셨습니다.", 400),
    UN_AUTHENTICATED("인증되지 않은 접근입니다.", 401),
    UN_DEFINED_ERROR("일시적인 오류가 발생했습니다. 잠시후에 다시 이용해주세요.", 500),
    METHOD_NOT_ALLOWED("허용하지 않는 http method 입니다.", 405),
    UNSUPPORTED_MEDIA_TYPE("지원하지 않는 미디어 타입 입니다..", 415),
    UN_HANDLED("알 수 없는 요청입니다..", 400),
    ACCESS_DENIED("접근이 거부되었습니다.", 403),

    // account error
    ACCOUNT_NOT_FOUND("존재하지 않는 계정입니다.", 404),
    ACCOUNT_TOKEN_NOT_VERIFIED(
        "아직 검증되지 않은 계정입니다.", 400),
    ACCOUNT_INVALID_EMAIL_TOKEN( "올바르지 않은 이메일 인증 토큰입니다.", 400),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다.", 409),
    DUPLICATE_USERNAME("이미 존재하는 사용자 명입니다.", 409),
    PASSWORD_NOT_MATCHED("비밀번호가 일치하지 않습니다.", 422),
    INVALID_PASSWORD_FORMAT("적절하지 않은 비밀번호 입력 방식입니다.", 422),
    INVALID_ACCOUNT("유효하지 않은 계정입니다.", 422),
    INVALID_PHONE_NUMBER("잘못된 소유자의 핸드폰 번호 입니다.", 422),
    INVALID_TOKEN("잘못된 토큰입니다.", 422),
    NOT_FOUND_REFRESH_TOKEN("리프래시 토큰을 찾을 수 없습니다.", 404),
    TOKEN_EXPIRED("이미 만료된 토큰입니다.", 422),

    // payment error
    PAYMENT_ORDER_NOT_FOUND("존재하지 않는 주문결제 입니다.", 404),
    INVALID_PAYMENT_ORDER("유효하지 않은 주문결제 입니다.", 422),
    INVALID_PAYMENT_ORDER_ACCOUNT_ID("유효하지 않은 회원의 주문결제 입니다.", 422),
    INVALID_PAYMENT_ORDER_NAME("유효하지 않은 주문결제 이름입니다.", 422),
    INVALID_PAYMENT_ORDER_AMOUNT("유효하지 않은 주문결제 수량입니다.", 422),
    INVALID_PAYMENT_ORDER_STATUS("유효하지 않은 주문결제 상태입니다.", 422),
    ALREADY_IN_PROGRESS_PAYMENT_ORDER("이미 진행중인 주문 결제 입니다.", 422),
    ALREADY_DONE_PAYMENT_ORDER("이미 완료된 주문 결제 입니다.", 422),
    ALREADY_CANCELED_PAYMENT_ORDER("이미 취소된 주문 결제 입니다.", 422),
    ALREADY_ABORTED_PAYMENT_ORDER("이미 실패한 주문 결제 입니다.", 422)
    ;
}