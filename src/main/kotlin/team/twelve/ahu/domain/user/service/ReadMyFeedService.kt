package team.twelve.ahu.domain.user.service

import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadAllFeedResponse
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import team.twelve.ahu.domain.user.presentation.dto.response.MyFeedResponse
import java.util.*

@Service
class ReadMyFeedService(
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun readMyFeed(userId: UUID): MyFeedResponse {
        val user = userRepository.findUserById(userId)
        val feeds = feedRepository.findAllByAuthor(user)
        val myFeedResponseList= feeds.map { feed ->
            ReadFeedResponse(
                id = feed.id,
                description = feed.description,
                author = feed.author,
                createTime = feed.createTime,
                updatedTime = feed.updateTime
            )
        }

        return MyFeedResponse(
            feeds = myFeedResponseList
        )
    }
}
