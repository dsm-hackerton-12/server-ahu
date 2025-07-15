package team.twelve.ahu.domain.auth.service

import org.springframework.stereotype.Service
import team.twelve.ahu.domain.user.entity.User
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import java.util.*

@Service
class UserService (
    private val userRepository: UserRepository
){
    fun findOrCreateUser(email: String, name: String): User {
        return userRepository.findByEmail(email)
            ?: userRepository.save(User(email = email, name = name))
    }

    fun findById(id : UUID): User =
        userRepository.findById(id).orElseThrow {NoSuchElementException("User not found")}
}