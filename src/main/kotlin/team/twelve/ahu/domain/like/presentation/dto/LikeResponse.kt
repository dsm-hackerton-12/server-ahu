package team.twelve.ahu.domain.like.presentation.dto

import team.twelve.ahu.domain.like.entity.Like

data class LikeResponse (
    val likeCount : Like?,
    val likeStatus : Boolean
) {
}