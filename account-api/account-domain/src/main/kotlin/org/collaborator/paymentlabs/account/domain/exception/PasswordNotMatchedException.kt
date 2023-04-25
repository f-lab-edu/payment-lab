package org.collaborator.paymentlabs.account.domain.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class PasswordNotMatchedException: ServiceException(ErrorCode.PASSWORD_NOT_MATCHED)