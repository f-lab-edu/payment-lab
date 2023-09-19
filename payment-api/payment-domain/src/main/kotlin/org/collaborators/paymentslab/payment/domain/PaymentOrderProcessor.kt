package org.collaborators.paymentslab.payment.domain

interface PaymentOrderProcessor {
    fun process(orderName: String, amount: Int): String
}