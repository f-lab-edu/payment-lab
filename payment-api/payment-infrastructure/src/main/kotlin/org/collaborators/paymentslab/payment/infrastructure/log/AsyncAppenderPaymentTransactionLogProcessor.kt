package org.collaborators.paymentslab.payment.infrastructure.log

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborators.paymentslab.payment.domain.PaymentOrderRecordEvent
import org.collaborators.paymentslab.payment.domain.PaymentResultEvent

import org.slf4j.LoggerFactory

class AsyncAppenderPaymentTransactionLogProcessor(
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger("payment")

    fun process(event: PaymentResultEvent) {
        logger.info(objectMapper.writeValueAsString(event))
    }

    fun process(event: PaymentOrderRecordEvent) {
        logger.info(objectMapper.writeValueAsString(event))
    }
}