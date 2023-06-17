package org.collaborators.paymentslab.payment.data

data class PageData(
    val pageNum: Int,
    val pageSize: Int,
    val direction: String,
    val properties: List<String>
)