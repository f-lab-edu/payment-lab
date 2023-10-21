package org.collaborators.paymentslab.payment.infrastructure.log

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async

open class AsyncAppenderPaymentTransactionLogProcessor(
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger("payment")

    @Async
    open fun process(event: PaymentResultEvent) {
        logger.info(objectMapper.writeValueAsString(event))
    }

    @Async
    open fun process(event: PaymentOrderRecordEvent) {
        logger.info(objectMapper.writeValueAsString(event))
    }
}