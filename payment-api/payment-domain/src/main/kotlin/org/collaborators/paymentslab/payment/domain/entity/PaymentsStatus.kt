package org.collaborators.paymentslab.payment.domain.entity
enum class PaymentsStatus {
    READY,
    IN_PROGRESS,
    DONE,
    CANCELED,
    ABORTED;

    companion object {
        fun isInRange(value: PaymentsStatus): Boolean {
            for (status in PaymentsStatus.values()) {
                if (status == value) return true
            }
            return false
        }
    }
}

