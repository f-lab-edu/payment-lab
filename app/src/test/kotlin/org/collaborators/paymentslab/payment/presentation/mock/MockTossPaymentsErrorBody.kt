package org.collaborators.paymentslab.payment.presentation.mock

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import java.nio.charset.StandardCharsets

object MockTossPaymentsErrorBody {
    val invalidCardNumber = generateHttpClientErrorException(
        errorBody = convertErrorBodyToString("INVALID_CARD_NUMBER", "카드번호를 다시 확인해주세요."))

    private fun generateHttpClientErrorException(httpStatus: HttpStatus? = HttpStatus.BAD_REQUEST, errorBody: String)
        : HttpClientErrorException {
        return HttpClientErrorException(
            httpStatus!!,
            httpStatus.reasonPhrase,
            null,
            errorBody.toByteArray(),
            StandardCharsets.UTF_8
        )
    }

    private fun convertErrorBodyToString(code: String, message: String): String {
        return "{\"code\": \"$code\", \"message\": \"$message\"}"
    }
}