package team.twelve.ahu.global.security.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/oauth2/test")
@Tag(name = "OAuth2 Test", description = "OAuth2 설정 테스트 API")
class OAuth2TestController {

    @Value("\${spring.security.oauth2.client.registration.google.client-id:NOT_SET}")
    private lateinit var clientId: String

    @Value("\${spring.security.oauth2.client.registration.google.client-secret:NOT_SET}")
    private lateinit var clientSecret: String

    @GetMapping("/config")
    fun getConfig(): Map<String, Any> {
        return mapOf(
            "clientId" to if (clientId != "NOT_SET") "✅ 설정됨" else "❌ 설정되지 않음",
            "clientSecret" to if (clientSecret != "NOT_SET") "✅ 설정됨" else "❌ 설정되지 않음",
            "redirectUri" to "http://localhost:8080/login/oauth2/code/google"
        )
    }
}