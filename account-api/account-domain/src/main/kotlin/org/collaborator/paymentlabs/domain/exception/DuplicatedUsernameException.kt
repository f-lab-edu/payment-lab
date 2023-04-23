package org.collaborator.paymentlabs.domain.exception

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class DuplicatedUsernameException: ServiceException(ErrorCode.DUPLICATE_USERNAME)