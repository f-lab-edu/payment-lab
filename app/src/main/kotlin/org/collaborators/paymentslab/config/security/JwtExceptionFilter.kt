package org.collaborators.paymentslab.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.error.InvalidTokenException
import org.collaborator.paymentlab.common.result.ApiResult
import org.collaborators.paymentslab.account.infrastructure.jwt.exception.AlreadyTokenExpiredException
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter

class JwtExceptionFilter(private val objectMapper: ObjectMapper): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: InvalidTokenException) {
            streamErrorResponse(response, exception)
        } catch (exception: AlreadyTokenExpiredException) {
            streamErrorResponse(response, exception)
        }
    }

    private fun streamErrorResponse(
        response: HttpServletResponse,
        exception: Exception
    ) {
        response.status = HttpStatus.BAD_REQUEST.value()
        response.contentType = "application/json; charset=UTF-8"
        response.writer.write(objectMapper.writeValueAsString(ApiResult.error(extractErrorCode(exception))))
    }

    private fun extractErrorCode(exception: Exception): ErrorCode {
        if (exception is AlreadyTokenExpiredException) {
            return ErrorCode.TOKEN_EXPIRED
        }
        return ErrorCode.INVALID_TOKEN
    }
}