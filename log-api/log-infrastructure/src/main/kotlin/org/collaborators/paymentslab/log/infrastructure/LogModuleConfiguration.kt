package org.collaborators.paymentslab.log.infrastructure

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(
    value = [
        AsyncAppenderPaymentTransactionLogProcessor::class
    ]
)
@Configuration
class LogModuleConfiguration {
}