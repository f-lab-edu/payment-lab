package org.collaborator.paymentlab.common.error

open class ServiceException : RuntimeException {
    val errorCode: ErrorCode

    constructor(message: String, errorCode: ErrorCode) : super(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : super(errorCode.msg) {
        this.errorCode = errorCode
    }
}