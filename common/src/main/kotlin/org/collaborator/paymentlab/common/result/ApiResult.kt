package org.collaborator.paymentlab.common.result

import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.ServiceException

class ApiResult<T> private constructor(val isSuccess: Boolean, val body: T) {
    companion object {
        fun <T> success(body: T): ApiResult<T> {
            return ApiResult(true, body)
        }

        fun error(errorCode: ErrorCode): ApiResult<ErrorBody> {
            return ApiResult(
                false,
                ErrorBody(errorCode.name, errorCode.msg)
            )
        }

        fun error(
            errorCode: ErrorCode,
            exception: ServiceException
        ): ApiResult<ErrorBody> {
            return ApiResult(
                false,
                ErrorBody(errorCode.name, exception.message!!)
            )
        }

        fun <T> error(body: T): ApiResult<T> {
            return ApiResult(false, body)
        }

        class ErrorBody(val code: String, val message: String)
    }
}