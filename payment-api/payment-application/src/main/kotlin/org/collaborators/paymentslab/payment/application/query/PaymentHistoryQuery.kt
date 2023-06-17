package org.collaborators.paymentslab.payment.application.query

data class PaymentHistoryQuery(
    val pageNum: Int,
    val pageSize: Int,
    val direction: String,
    val properties: List<String>
) {
    companion object {
        fun of(pageNum: Int, pageSize: Int, direction: String = "DESC", vararg properties: String = arrayOf("id")): PaymentHistoryQuery {
            return PaymentHistoryQuery(pageNum, pageSize, direction, properties.toList())
        }
    }
}
