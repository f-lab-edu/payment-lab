package org.collaborators.paymentslab.config.resilience4j

import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer
import io.github.resilience4j.core.IntervalFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val TOSS_PAYMENTS_APPROVAL = "tossPaymentsApprovalProcessor"
private const val INITIAL_INTERVAL = 1000L
private const val MULTIPLIER = 2.0
private const val RANDOMIZATION_FACTOR = 0.6
private const val MAX_RETRIES = 4

@Configuration
class Resilience4jConfig {
    @Bean
    fun retryConfigCustomizer(): RetryConfigCustomizer {
        val jitterBackoffFunction
            = IntervalFunction.ofExponentialRandomBackoff(INITIAL_INTERVAL, MULTIPLIER, RANDOMIZATION_FACTOR)

        return RetryConfigCustomizer
            .of(TOSS_PAYMENTS_APPROVAL) {
                it.maxAttempts(MAX_RETRIES)
                    .intervalFunction(jitterBackoffFunction)
                    .build()
            }
    }
}