package org.collaborators.paymentslab.payment.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class TossPaymentsCardInfo {
    protected fun TossPaymentsCardInfo() {}

    private var company: String? = null
    private var number: String? = null
    private var installmentPlanMonths: Int? = null
    private var approveNo: String? = null
    private var useCardPoint = false
    private var cardType: String? = null
    private var ownerType: String? = null
    private var receiptUrl: String? = null

    @Enumerated(EnumType.STRING)
    private var acquireStatus: TossPaymentsAcquireStatus? = null
    private var isInterestFree = false

    fun TossPaymentsCardInfo(
        company: String?, number: String?, installmentPlanMonths: Int?,
        approveNo: String?, useCardPoint: Boolean, cardType: String?, ownerType: String?,
        receiptUrl: String?,
        acquireStatus: TossPaymentsAcquireStatus?, isInterestFree: Boolean
    ) {
        this.company = company
        this.number = number
        this.installmentPlanMonths = installmentPlanMonths
        this.approveNo = approveNo
        this.useCardPoint = useCardPoint
        this.cardType = cardType
        this.ownerType = ownerType
        this.receiptUrl = receiptUrl
        this.acquireStatus = acquireStatus
        this.isInterestFree = isInterestFree
    }

    enum class TossPaymentsAcquireStatus(private val description: String) {
        READY("매입 대기"), REQUESTED("매입 요청됨"), COMPLETED("매입 완료"), CANCEL_REQUESTED("매입 취소 요청됨"), CANCELED("매입 취소 완료");
    }
}