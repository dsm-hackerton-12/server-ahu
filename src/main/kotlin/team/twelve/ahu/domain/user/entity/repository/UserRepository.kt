package team.twelve.ahu.domain.user.entity.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.twelve.ahu.domain.user.entity.User
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findByGoogleSub(googleSub: String?): User?

    fun findUserById(userId: UUID): User
}