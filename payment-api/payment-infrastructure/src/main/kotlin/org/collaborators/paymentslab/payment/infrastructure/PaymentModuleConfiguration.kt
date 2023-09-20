package org.collaborators.paymentslab.payment.infrastructure

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import org.collaborators.paymentslab.payment.infrastructure.jpa.PaymentOrderRepositoryAdapter
import org.collaborators.paymentslab.payment.infrastructure.jpa.TossPaymentHistoryRepositoryAdapter
import org.collaborators.paymentslab.payment.infrastructure.jpa.TossPaymentsRepositoryAdapter
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Import(value = [
    TossPaymentsRepositoryAdapter::class,
    TossPaymentHistoryRepositoryAdapter::class,
    PaymentOrderRepositoryAdapter::class,
    TossPaymentsProcessor::class,
    TossPaymentsQueryManager::class,
    TossPaymentsValidator::class,
    TossPaymentsKeyInApprovalProcessor::class,
    TossPaymentsPaymentOrderProcessor::class,
])
@Configuration
class PaymentModuleConfiguration