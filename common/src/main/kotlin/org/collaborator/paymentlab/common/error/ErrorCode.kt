package org.collaborator.paymentlab.common.error

enum class ErrorCode(
    val msg: String,
    val status: Int
) {
    ACCOUNT_NOT_FOUND("존재하지 않는 계정입니다.", 404),
    ACCOUNT_TOKEN_NOT_VERIFIED(
        "아직 검증되지 않은 계정입니다.", 400),
    ACCOUNT_INVALID_EMAIL_TOKEN( "올바르지 않은 이메일 인증 토큰입니다.", 400),
    DUPLICATE_EMAIL("이미 존재하는 이메일입니다.", 400),
    DUPLICATE_USERNAME("이미 존재하는 사용자 명입니다.", 400),
    PASSWORD_NOT_MATCHED("비밀번호가 일치하지 않습니다.", 400),
    INVALID_PASSWORD_FORMAT("적절하지 않은 비밀번호 입력 방식입니다.", 400),
    INVALID_ACCOUNT("유효하지 않은 계정입니다.", 400 );

    fun getMessage(): String {
        return msg
    }
}