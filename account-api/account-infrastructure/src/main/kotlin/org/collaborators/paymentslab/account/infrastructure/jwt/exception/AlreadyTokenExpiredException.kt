package org.collaborators.paymentslab.account.infrastructure.jwt.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class AlreadyTokenExpiredException: ServiceException(ErrorCode.TOKEN_EXPIRED)