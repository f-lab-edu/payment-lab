package org.collaborator.paymentlab.domain.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class DuplicatedUsernameException: ServiceException(ErrorCode.DUPLICATE_USERNAME)