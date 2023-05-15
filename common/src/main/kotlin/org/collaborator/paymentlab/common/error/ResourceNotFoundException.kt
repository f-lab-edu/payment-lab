package org.collaborator.paymentlab.common.error

class ResourceNotFoundException(errorCode: ErrorCode) : ServiceException(errorCode)