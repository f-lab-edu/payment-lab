package org.collaborators.paymentslab.log.infrastructure

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(
    value = [
        PaymentEventResultSyncRecorder::class
    ]
)
@Configuration
class LogModuleConfiguration {
}