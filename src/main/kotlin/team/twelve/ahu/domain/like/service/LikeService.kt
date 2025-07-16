package team.twelve.ahu.domain.like.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.like.entity.Like
import team.twelve.ahu.domain.like.entity.repository.LikeRepository
import team.twelve.ahu.domain.like.presentation.dto.LikeResponse
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import java.util.*

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val userRepository: UserRepository,
    private val feedRepository: FeedRepository
) {

    @Transactional
    fun toggleLike(userId: UUID, feedId: UUID): LikeResponse {
        val user = userRepository.findUserById(userId)
        val feed = feedRepository.findById(feedId)
            .orElseThrow { IllegalArgumentException("Feed not found") }

        val existingLike = likeRepository.findByUserAndFeed(user, feed)

        return if (existingLike != null) {
            likeRepository.delete(existingLike)
            LikeResponse(likeCount = null, likeStatus = false)
        } else {
            val like = likeRepository.save(
                Like(feed = feed, user = user, likeCount = 1, likeStatus = true)
            )
            LikeResponse(likeCount = like, likeStatus = true)
        }
    }
}
