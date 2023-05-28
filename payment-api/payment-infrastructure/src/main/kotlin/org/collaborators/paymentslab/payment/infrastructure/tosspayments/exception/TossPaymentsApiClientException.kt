package org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception

import org.springframework.web.client.HttpClientErrorException
import java.nio.charset.StandardCharsets

class TossPaymentsApiClientException(exception: HttpClientErrorException)
    : HttpClientErrorException(
    exception.message!!, exception.statusCode, exception.statusText,
    exception.responseHeaders, exception.responseBodyAsByteArray, StandardCharsets.UTF_8)