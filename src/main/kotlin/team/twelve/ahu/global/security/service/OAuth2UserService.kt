package team.twelve.ahu.global.security.service

import org.springframework.stereotype.Service
import team.twelve.ahu.domain.user.entity.User
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import java.util.*

@Service
class OAuth2UserService(
    private val userRepository: UserRepository
) {
    
    fun findOrCreateUserByOAuth2(googleSub: String?, email: String, name: String?): User {
        // 먼저 googleSub로 찾기
        if (googleSub != null) {
            userRepository.findByGoogleSub(googleSub)?.let { return it }
        }
        
        // 그 다음 email로 찾기
        userRepository.findByEmail(email)?.let { existingUser ->
            // 기존 사용자에 googleSub 업데이트
            return if (existingUser.googleSub == null && googleSub != null) {
                userRepository.save(existingUser.copy(googleSub = googleSub))
            } else {
                existingUser
            }
        }
        
        // 없으면 새로 생성
        return userRepository.save(
            User(
                googleSub = googleSub,
                email = email,
                name = name
            )
        )
    }
    
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    
    fun findById(id: UUID): User? {
        return userRepository.findById(id).orElse(null)
    }
}