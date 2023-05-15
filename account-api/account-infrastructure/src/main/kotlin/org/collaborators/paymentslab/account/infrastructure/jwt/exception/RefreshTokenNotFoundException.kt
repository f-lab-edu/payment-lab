package org.collaborators.paymentslab.account.infrastructure.jwt.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class RefreshTokenNotFoundException: ServiceException(ErrorCode.NOT_FOUND_REFRESH_TOKEN)