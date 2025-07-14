package team.twelve.ahu.domain.auth.presentation.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.twelve.ahu.domain.auth.entity.User
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email : String) : User?
}