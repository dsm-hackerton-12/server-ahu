package team.twelve.ahu.domain.user.presentation.dto.response

import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse

data class MyFeedResponse (
    val feeds: List<ReadFeedResponse>
)