package team.twelve.ahu.domain.like.presentation.dto

import team.twelve.ahu.domain.like.entity.Like

data class LikeResponse (
    val likeCount : Int,
    val likeStatus : Boolean
) {
}