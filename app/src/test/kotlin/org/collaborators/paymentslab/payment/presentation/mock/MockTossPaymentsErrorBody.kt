package org.collaborators.paymentslab.payment.presentation.mock

import org.collaborator.paymentlab.common.result.ApiResult
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import java.nio.charset.StandardCharsets

object MockTossPaymentsErrorBody {
    val invalidCardNumber = HttpClientErrorException(
        HttpStatus.BAD_REQUEST,
        HttpStatus.BAD_REQUEST.reasonPhrase,
        null,
        convertErrorBodyToString("INVALID_CARD_NUMBER", "카드번호를 다시 확인해주세요.").toByteArray(),
        StandardCharsets.UTF_8
    )

    private fun convertErrorBodyToString(code: String, message: String): String {
        return "{\"code\": \"$code\", \"message\": \"$message\"}"
    }
}