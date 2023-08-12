package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.entity.PaymentsStatus
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder

class TossPaymentsValidator {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun validate(paymentOrder: PaymentOrder, amount: Int, orderName: String) {
        val accountUser = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser
        val paymentOrderId = paymentOrder.id

        if (paymentOrder.accountId != accountUser.id) {
            log.error("invalid accountId from paymentOrderId {}", paymentOrderId)
            throw InvalidPaymentOrderAccountIdException()
        }
        if (paymentOrder.amount != amount) {
            log.error("invalid amount from paymentOrderId {}", paymentOrderId)
            throw InvalidPaymentOrderAmountException()
        }
        if (paymentOrder.orderName != orderName) {
            log.error("invalid orderName from paymentOrderId {}", paymentOrderId)
            throw InvalidPaymentOrderNameException()
        }
        if (!PaymentsStatus.isInRange(paymentOrder.status)) {
            log.error("invalid paymentOrderStatus from paymentOrderId {}", paymentOrderId)
            throw InvalidPaymentOrderStatusException()
        }
        if (paymentOrder.status == PaymentsStatus.IN_PROGRESS) {
            log.error("already in progress paymentOrderId {}", paymentOrderId)
            throw AlreadyInProgressPaymentOrderException()
        }
        if (paymentOrder.status == PaymentsStatus.DONE) {
            log.error("already done paymentOrderId {}", paymentOrderId)
            throw AlreadyDonePaymentOrderException()
        }
        if (paymentOrder.status == PaymentsStatus.CANCELED) {
            log.error("already canceled paymentOrderId {}", paymentOrderId)
            throw AlreadyCanceledPaymentOrderException()
        }
        if (paymentOrder.status == PaymentsStatus.ABORTED) {
            log.error("already aborted paymentOrderId {}", paymentOrderId)
            throw AlreadyAbortedPaymentOrderException()
        }
    }
}