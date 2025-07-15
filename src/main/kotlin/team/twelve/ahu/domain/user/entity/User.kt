package team.twelve.ahu.domain.user.entity

import jakarta.persistence.*
import java.util.UUID

@Entity(name = "user")
data class User(
    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "google_sub", nullable = true, unique = true)
    val googleSub: String?,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name")
    var name: String? = null

) {
    constructor() : this(UUID.randomUUID(), null, "", null)

    constructor(email: String, name: String) : this(UUID.randomUUID(), null, email, name)
}
