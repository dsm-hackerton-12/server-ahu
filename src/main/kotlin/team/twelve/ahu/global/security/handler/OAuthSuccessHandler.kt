package team.twelve.ahu.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import team.twelve.ahu.global.security.jwt.JwtTokenProvider
import team.twelve.ahu.global.security.service.OAuth2UserService
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Component
class OAuthSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider,
    private val oAuth2UserService: OAuth2UserService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        requireNotNull(response)
        requireNotNull(authentication)

        val oAuth2User = authentication.principal as OAuth2User
        val googleSub = oAuth2User.attributes["sub"] as? String
        val email = oAuth2User.attributes["email"] as? String
            ?: throw IllegalArgumentException("Email not found in attributes")
        val name = oAuth2User.attributes["name"] as? String

        val user = oAuth2UserService.findOrCreateUserByOAuth2(googleSub, email, name)
        val accessToken = jwtTokenProvider.generateToken(user)

        // ✅ 인코딩 필수
        val encodedEmail = URLEncoder.encode(user.email, StandardCharsets.UTF_8)
        val encodedName = URLEncoder.encode(user.name ?: "", StandardCharsets.UTF_8)
        val encodedUserId = URLEncoder.encode(user.id.toString(), StandardCharsets.UTF_8)

        val redirectUrl =
            "http://localhost:5173/oauth/callback" +
                    "?accessToken=$accessToken" +
                    "&email=$encodedEmail" +
                    "&name=$encodedName" +
                    "&userId=$encodedUserId"

        response.sendRedirect(redirectUrl)
    }
}
