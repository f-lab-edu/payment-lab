package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborator.paymentlab.common.AuthenticatedUser
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.entity.PaymentsStatus
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Locale


class TossPaymentsValidator {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val statusToException = mapOf(
        PaymentsStatus.IN_PROGRESS to AlreadyInProgressPaymentOrderException(),
        PaymentsStatus.DONE to AlreadyDonePaymentOrderException(),
        PaymentsStatus.CANCELED to AlreadyCanceledPaymentOrderException(),
        PaymentsStatus.ABORTED to AlreadyAbortedPaymentOrderException()
    )

    fun validate(paymentOrder: PaymentOrder, amount: Int, orderName: String) {
        val accountUser = SecurityContextHolder.getContext().authentication.principal as AuthenticatedUser

        checkAccountId(paymentOrder, accountUser.id)
        checkPaymentDetails(paymentOrder, amount, orderName)
        checkPaymentStatus(paymentOrder)
    }

    private fun checkAccountId(paymentOrder: PaymentOrder, accountId: Long) {
        if (paymentOrder.accountId != accountId) {
            logAndThrow("invalid accountId from paymentOrderId ${paymentOrder.id}", InvalidPaymentOrderAccountIdException())
        }
    }

    private fun checkPaymentDetails(paymentOrder: PaymentOrder, amount: Int, orderName: String) {
        if (paymentOrder.amount != amount || paymentOrder.orderName != orderName || !PaymentsStatus.isInRange(paymentOrder.status)) {
            logAndThrow("invalid amount from paymentOrderId ${paymentOrder.id}", InvalidPaymentOrderException())
        }
    }

    private fun checkPaymentStatus(paymentOrder: PaymentOrder) {
        statusToException[paymentOrder.status]?.let {
            logAndThrow("already ${paymentOrder.status.name.lowercase(Locale.getDefault())} paymentOrderId ${paymentOrder.id}", it)
        }
    }

    private fun logAndThrow(message: String, exception: RuntimeException) {
        log.error(message)
        throw exception
    }
}