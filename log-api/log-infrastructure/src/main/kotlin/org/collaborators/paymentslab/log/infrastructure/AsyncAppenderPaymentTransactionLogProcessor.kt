package org.collaborators.paymentslab.log.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.collaborators.paymentslab.log.domain.TransactionLogProcessor
import org.slf4j.LoggerFactory

class AsyncAppenderPaymentTransactionLogProcessor(
    private val objectMapper: ObjectMapper
): TransactionLogProcessor<PaymentResultEvent> {

    private val logger = LoggerFactory.getLogger("payment")

    override fun execute(event: PaymentResultEvent) {
        logger.info(objectMapper.writeValueAsString(event))
    }
}