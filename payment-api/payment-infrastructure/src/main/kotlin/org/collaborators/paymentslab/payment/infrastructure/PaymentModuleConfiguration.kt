package org.collaborators.paymentslab.payment.infrastructure

import org.collaborators.paymentslab.payment.infrastructure.jpa.PaymentOrderRepositoryAdapter
import org.collaborators.paymentslab.payment.infrastructure.jpa.TossPaymentHistoryRepositoryAdapter
import org.collaborators.paymentslab.payment.infrastructure.jpa.TossPaymentsRepositoryAdapter
import org.collaborators.paymentslab.payment.infrastructure.log.AsyncAppenderPaymentTransactionLogProcessor
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.*
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(value = [
    TossPaymentsRestClient::class,
    PaymentPropertiesResolver::class,
    TossPaymentsTransactionEventPublisher::class,
    TossPaymentsRepositoryAdapter::class,
    TossPaymentHistoryRepositoryAdapter::class,
    PaymentOrderRepositoryAdapter::class,
    TossPaymentsProcessor::class,
    TossPaymentsQueryManager::class,
    TossPaymentsValidator::class,
    TossPaymentsKeyInApprovalProcessor::class,
    TossPaymentsPaymentOrderProcessor::class,
    AsyncAppenderPaymentTransactionLogProcessor::class
])
@Configuration
class PaymentModuleConfiguration