package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadAllFeedResponse
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.feed.presentation.repository.FeedRepository

@Service
class ReadAllService(
    private val feedRepository: FeedRepository,
) {
    @Transactional
    fun readAllFeed() : ReadAllFeedResponse {
        val feeds = feedRepository.findAll()

        val readFeedResponseList = feeds.map { feed ->
            ReadFeedResponse(
                id = feed.id,
                description = feed.description,
                author = feed.author,
                createTime = feed.createTime,
                updatedTime = feed.updateTime
            )
        }

        return ReadAllFeedResponse(
            feeds = readFeedResponseList
        )
    }
}