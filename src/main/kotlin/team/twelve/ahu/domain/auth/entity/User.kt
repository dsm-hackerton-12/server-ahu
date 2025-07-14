package team.twelve.ahu.domain.auth.entity

import jakarta.persistence.*
import java.util.UUID

@Entity(name = "user")
class User(
    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String

) {
    protected constructor() : this(UUID.randomUUID(), "", "")
}
