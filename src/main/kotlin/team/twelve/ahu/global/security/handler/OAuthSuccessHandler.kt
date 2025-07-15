package team.twelve.ahu.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import team.twelve.ahu.global.security.jwt.JwtTokenProvider
import team.twelve.ahu.global.security.service.OAuth2UserService

@Component
class OAuthSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper,
    private val oAuth2UserService: OAuth2UserService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        requireNotNull(response) { "response is null" }
        requireNotNull(authentication) { "authentication is null" }

        val oAuth2User = authentication.principal as OAuth2User
        val googleSub = oAuth2User.attributes["sub"] as? String
        val email = oAuth2User.attributes["email"] as? String
            ?: throw IllegalArgumentException("Email not found in attributes")
        val name = oAuth2User.attributes["name"] as? String

        // 사용자 찾기 또는 생성
        val user = oAuth2UserService.findOrCreateUserByOAuth2(googleSub, email, name)
        
        // 정상적인 JWT 토큰 생성
        val accessToken = jwtTokenProvider.generateToken(user)

        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_OK
        val responseBody = mapOf(
            "accessToken" to accessToken,
            "email" to user.email,
            "name" to user.name,
            "userId" to user.id.toString()
        )
        objectMapper.writeValue(response.writer, responseBody)
    }
}