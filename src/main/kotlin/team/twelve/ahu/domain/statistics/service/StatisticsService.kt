package team.twelve.ahu.domain.statistics.service

import org.springframework.stereotype.Service
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.like.entity.repository.LikeRepository
import team.twelve.ahu.domain.statistics.presentation.dto.response.KeywordStatisticsResponse
import team.twelve.ahu.domain.statistics.presentation.dto.response.OverallStatisticsResponse
import team.twelve.ahu.domain.word.entity.repository.WordRepository

@Service
class StatisticsService(
    private val feedRepository: FeedRepository,
    private val likeRepository: LikeRepository,
    private val wordRepository: WordRepository
) {

    fun getKeywordStatistics(keyword: String): KeywordStatisticsResponse {
        val word = wordRepository.findByWord(keyword)
            ?: throw IllegalArgumentException("Word not found: $keyword")
        
        val aiFeeds = feedRepository.findAllByWord(word).filter { it.writtenByAi }
        val humanFeeds = feedRepository.findAllByWord(word).filter { !it.writtenByAi }
        
        val aiLikeCount = aiFeeds.sumOf { likeRepository.countByFeed(it) }
        val humanLikeCount = humanFeeds.sumOf { likeRepository.countByFeed(it) }
        
        val avgAiLikes = if (aiFeeds.isNotEmpty()) aiLikeCount.toDouble() / aiFeeds.size else 0.0
        val avgHumanLikes = if (humanFeeds.isNotEmpty()) humanLikeCount.toDouble() / humanFeeds.size else 0.0
        
        return KeywordStatisticsResponse(
            keyword = keyword,
            aiPostCount = aiFeeds.size,
            humanPostCount = humanFeeds.size,
            totalAiLikes = aiLikeCount,
            totalHumanLikes = humanLikeCount,
            averageAiLikes = avgAiLikes,
            averageHumanLikes = avgHumanLikes
        )
    }

    fun getOverallStatistics(): OverallStatisticsResponse {
        val allFeeds = feedRepository.findAll().toList()
        val aiFeeds = allFeeds.filter { it.writtenByAi }
        val humanFeeds = allFeeds.filter { !it.writtenByAi }
        
        val aiLikeCount = aiFeeds.sumOf { likeRepository.countByFeed(it) }
        val humanLikeCount = humanFeeds.sumOf { likeRepository.countByFeed(it) }
        
        val avgAiLikes = if (aiFeeds.isNotEmpty()) aiLikeCount.toDouble() / aiFeeds.size else 0.0
        val avgHumanLikes = if (humanFeeds.isNotEmpty()) humanLikeCount.toDouble() / humanFeeds.size else 0.0
        
        return OverallStatisticsResponse(
            totalAiPosts = aiFeeds.size,
            totalHumanPosts = humanFeeds.size,
            totalAiLikes = aiLikeCount,
            totalHumanLikes = humanLikeCount,
            averageAiLikes = avgAiLikes,
            averageHumanLikes = avgHumanLikes,
            totalPosts = allFeeds.size,
            totalLikes = aiLikeCount + humanLikeCount
        )
    }
}