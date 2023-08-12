package org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class PaymentOrderNotFoundException: ServiceException(ErrorCode.PAYMENT_ORDER_NOT_FOUND)