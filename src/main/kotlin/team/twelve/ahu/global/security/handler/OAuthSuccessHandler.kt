package team.twelve.ahu.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import team.twelve.ahu.global.security.jwt.JwtTokenProvider

class OAuthSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper,
    private val userRepository: UserRepository
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        requireNotNull(response) { "response is null" }
        requireNotNull(authentication) { "authentication is null" }

        val oAuth2User = authentication.principal as OAuth2User
        val email = oAuth2User.attributes["email"] as? String
            ?: throw IllegalArgumentException("Email not found in attributes")

        val user = userRepository.findByEmail(email)
            ?: throw IllegalStateException("User not found with email: $email")

        if (user.name.isNullOrBlank()) {
            response.sendRedirect("http://localhost:3000/signup/info?email=${user.email}")
            return
        }

        val token = jwtTokenProvider.generateToken(user)

        response.sendRedirect("http://localhost:3000?accessToken=$token")
    }
}