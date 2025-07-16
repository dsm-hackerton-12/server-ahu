package team.twelve.ahu.domain.like.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.like.entity.Like
import team.twelve.ahu.domain.like.entity.repository.LikeRepository
import team.twelve.ahu.domain.like.presentation.dto.LikeResponse
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import team.twelve.ahu.global.security.jwt.JwtTokenProvider
import java.util.*

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val userRepository: UserRepository,
    private val feedRepository: FeedRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional
    fun toggleLike(token: String, feedId: UUID): LikeResponse {
        val cleanToken = token.removePrefix("Bearer ")
        val userId = jwtTokenProvider.extractUserId(cleanToken)
        val user = userRepository.findUserById(userId)
        val feed = feedRepository.findById(feedId)
            .orElseThrow { IllegalArgumentException("Feed not found") }

        val existingLike = likeRepository.findByUserAndFeed(user, feed)

        if (existingLike != null) {
            likeRepository.delete(existingLike)
        } else {
            likeRepository.save(Like(feed = feed, user = user))
        }

        val likeCount = likeRepository.countByFeed(feed)
        val likeStatus = likeRepository.existsByUserAndFeed(user, feed)

        return LikeResponse(likeCount = likeCount, likeStatus = likeStatus)
    }

}
