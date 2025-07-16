package team.twelve.ahu.domain.like.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.user.entity.User

@Entity(name = "like")
data class Like(

    @ManyToOne(fetch = FetchType.LAZY)
    val feed : Feed,

    @ManyToOne(fetch = FetchType.LAZY)
    val user : User,

    @Column(name = "like_count")
    val likeCount : Int,

    @Column(name = "Boolean")
    val likeStatus : Boolean
){

}