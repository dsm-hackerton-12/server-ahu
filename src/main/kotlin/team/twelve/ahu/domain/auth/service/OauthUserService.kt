package team.twelve.ahu.domain.auth.service

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import team.twelve.ahu.domain.user.entity.User
import team.twelve.ahu.global.security.jwt.JwtTokenProvider

@Service
class OauthUserService(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val attributes = oAuth2User.attributes

        val email = attributes["email"] as? String
            ?: throw IllegalArgumentException("Email not found in Google Profile")

        val sub = attributes["sub"] as? String
            ?: throw IllegalArgumentException("Google sub ID not found")

        val user: User = userService.findByGoogleSub(sub, email)

        val token = jwtTokenProvider.generateToken(user)

        val extendedAttributes = attributes.toMutableMap()
        extendedAttributes["accessToken"] = token

        return DefaultOAuth2User(
            oAuth2User.authorities,
            extendedAttributes,
            "email"
        )
    }
}