package team.twelve.ahu.domain.statistics.presentation.dto.response

data class KeywordStatisticsResponse(
    val keyword: String,
    val aiPostCount: Int,
    val humanPostCount: Int,
    val totalAiLikes: Int,
    val totalHumanLikes: Int,
    val averageAiLikes: Double,
    val averageHumanLikes: Double
)