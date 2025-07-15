package team.twelve.ahu.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NaverApiConfig {
    @Value("\${naver.api.client-id}")
    lateinit var clientId: String
    
    @Value("\${naver.api.client-secret}")
    lateinit var clientSecret: String
}