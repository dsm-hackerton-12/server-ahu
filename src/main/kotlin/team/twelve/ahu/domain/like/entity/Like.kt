package team.twelve.ahu.domain.like.entity

import jakarta.persistence.*
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.user.entity.User
import java.util.*

@Entity(name = "user_like")
data class Like(
    @Id
    @Column(name = "like_id", columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    val feed : Feed,

    @ManyToOne(fetch = FetchType.LAZY)
    val user : User,

    @Column(name = "like_count")
    val likeCount : Int = 0,

    @Column(name = "Boolean")
    val likeStatus : Boolean = false
){

}