package team.twelve.ahu.domain.feed.presentation.dto.request

import team.twelve.ahu.domain.user.entity.User


data class CreateFeedRequest(
    val description: String,
    val author: User
) {

}