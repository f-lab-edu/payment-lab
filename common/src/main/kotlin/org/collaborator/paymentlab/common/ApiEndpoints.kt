package org.collaborator.paymentlab.common

/* Account domain api signature */
const val V1_AUTH = "/v1/auth"
const val REGISTER = "register"
const val REGISTER_ADMIN = "register/admin"
const val CONFIRM = "confirm"
const val LOGIN = "login"
const val RE_ISSUANCE = "reIssuance"

/* Payment domain api signature */
const val V1_TOSS_PAYMENTS = "/v1/toss-payments"
const val KEY_IN_PAYMENT_ORDER_ID = "key-in/{paymentOrderId}"
const val PAYMENT_ORDER = "payment-order"