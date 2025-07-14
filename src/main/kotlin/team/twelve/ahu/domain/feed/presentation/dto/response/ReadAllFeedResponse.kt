package team.twelve.ahu.domain.feed.presentation.dto.response

data class ReadAllFeedResponse(
    val feedList: List<ReadFeedResponse>,
)