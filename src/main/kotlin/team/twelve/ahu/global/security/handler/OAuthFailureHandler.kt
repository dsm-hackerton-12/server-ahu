package team.twelve.ahu.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

class OAuthFailureHandler(
    private val objectMapper: ObjectMapper
) : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        requireNotNull(response) { "response is null" }

        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val errorResponse = mapOf(
            "status" to HttpServletResponse.SC_UNAUTHORIZED,
            "error" to "Unauthorized",
            "message" to (exception?.message ?: "Authentication failed"),
            "path" to request?.requestURI
        )

        try {
            objectMapper.writeValue(response.writer, errorResponse)
        } catch (e: Exception) {
            // 로그 처리 또는 기본 에러 메시지 작성
            response.writer.write("{\"error\":\"Authentication failed\"}")
        }
    }
}