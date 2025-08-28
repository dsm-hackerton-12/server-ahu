package team.twelve.ahu.domain.statistics.presentation.dto.response

data class OverallStatisticsResponse(
    val totalAiPosts: Int,
    val totalHumanPosts: Int,
    val totalAiLikes: Int,
    val totalHumanLikes: Int,
    val averageAiLikes: Double,
    val averageHumanLikes: Double,
    val totalPosts: Int,
    val totalLikes: Int
)