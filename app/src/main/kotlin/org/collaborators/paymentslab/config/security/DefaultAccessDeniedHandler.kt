package org.collaborators.paymentslab.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.ServletOutputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.result.ApiResult
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException

class DefaultAccessDeniedHandler(
    private val objectMapper: ObjectMapper): AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpStatus.FORBIDDEN.value()
        val outputStream = response.outputStream
        objectMapper.writeValue(outputStream, ApiResult.error(ErrorCode.ACCESS_DENIED))
    }
}