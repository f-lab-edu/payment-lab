package org.collaborators.paymentslab.payment.infrastructure.tosspayments

import org.collaborators.paymentslab.payment.domain.*
import org.collaborators.paymentslab.payment.domain.entity.PayMethod
import org.collaborators.paymentslab.payment.domain.entity.TossPayments
import org.collaborators.paymentslab.payment.domain.entity.TossPaymentsInfo

object TossPaymentsFactory {

    fun create(responseBody: TossPaymentsApprovalResponse): TossPayments {
        return TossPayments(createInfo(responseBody), null, createCardInfo(responseBody), responseBody.status, PayMethod.CARD)
    }

    private fun createCardInfo(responseBody: TossPaymentsApprovalResponse): TossPaymentsCardInfo {
        return TossPaymentsCardInfo(
            amount = responseBody.card.amount,
            issuerCode = responseBody.card.issuerCode,
            acquirerCode = responseBody.card.acquirerCode,
            number = responseBody.card.number,
            installmentPlanMonths = responseBody.card.installmentPlanMonths,
            approveNo = responseBody.card.approveNo,
            useCardPoint = responseBody.card.useCardPoint,
            cardType = responseBody.card.cardType,
            ownerType = responseBody.card.ownerType,
            acquireStatus = TossPaymentsCardInfo.TossPaymentsAcquireStatus.valueOf(responseBody.card.acquireStatus),
            isInterestFree = responseBody.card.isInterestFree,
            interestPayer = null
        )
    }

    private fun createInfo(responseBody: TossPaymentsApprovalResponse): TossPaymentsInfo {
        return TossPaymentsInfo(
            version = responseBody.version,
            paymentKey = responseBody.paymentKey,
            type = responseBody.type,
            orderId = responseBody.orderId,
            orderName = responseBody.orderName,
            mid = responseBody.mId,
            currency = responseBody.currency,
            method = responseBody.method,
            totalAmount = responseBody.totalAmount,
            balanceAmount = responseBody.balanceAmount,
            requestedAt = responseBody.requestedAt,
            approvedAt = responseBody.approvedAt,
            useEscrow = responseBody.useEscrow,
            lastTransactionKey = responseBody.lastTransactionKey,
            suppliedAmount = responseBody.suppliedAmount,
            vat = responseBody.vat,
            cultureExpense = responseBody.cultureExpense,
            taxFreeAmount = responseBody.taxFreeAmount,
            taxExemptionAmount = responseBody.taxExemptionAmount
        )
    }
}