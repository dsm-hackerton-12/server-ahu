package team.twelve.ahu.domain.feed.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import team.twelve.ahu.domain.feed.presentation.dto.request.UpdateFeedRequest
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateFeedService(
    private val feedRepository: FeedRepository,
) {
    @Transactional
    fun execute(id: UUID, request: UpdateFeedRequest) {
        val feed = feedRepository.findFeedById(id)
        val updatedFeed = feed.copy(
            description = request.description,
            updateTime = LocalDateTime.now()
        )
        feedRepository.save(updatedFeed)
    }
}