package org.collaborators.paymentslab.payment.infrastructure.tosspayments.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class InvalidPaymentOrderNameException: ServiceException(ErrorCode.INVALID_PAYMENT_ORDER_NAME)