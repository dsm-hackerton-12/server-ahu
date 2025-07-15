package team.twelve.ahu.domain.user.service

import org.springframework.stereotype.Service
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import java.util.*

@Service
class UpdateNameService(
    private val userRepository: UserRepository
) {
    fun updateName(userId: UUID, newName: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User not found") }

        user.name = newName
        userRepository.save(user)
    }
}