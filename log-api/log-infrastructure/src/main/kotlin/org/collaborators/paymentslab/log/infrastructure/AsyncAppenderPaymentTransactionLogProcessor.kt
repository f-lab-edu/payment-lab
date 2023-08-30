package org.collaborators.paymentslab.log.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.slf4j.LoggerFactory

class AsyncAppenderPaymentTransactionLogProcessor(
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger("payment")

    fun process(event: PaymentResultEvent) {
        logger.info(objectMapper.writeValueAsString(event))
    }
}