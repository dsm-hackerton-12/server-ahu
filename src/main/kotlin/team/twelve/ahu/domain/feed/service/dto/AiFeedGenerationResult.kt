package team.twelve.ahu.domain.feed.service.dto

data class AiFeedGenerationResult(
    val totalWords: Int,
    val generatedFeeds: Int,
    val skippedWords: Int
)