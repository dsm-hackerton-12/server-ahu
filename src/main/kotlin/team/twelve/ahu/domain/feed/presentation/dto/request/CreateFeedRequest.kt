package team.twelve.ahu.domain.feed.presentation.dto.request

data class CreateFeedRequest(
    val title: String,
    val description: String,
    val author: String
) {

}