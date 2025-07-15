package team.twelve.ahu.domain.feed.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import team.twelve.ahu.domain.feed.presentation.dto.request.UpdateFeedRequest
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import team.twelve.ahu.global.exception.InvalidRequestException
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateFeedService(
    private val feedRepository: FeedRepository,
) {
    @Transactional
    fun execute(id: UUID, request: UpdateFeedRequest) {
        if (request.description.isBlank()) {
            throw InvalidRequestException("피드 내용은 비어있을 수 없습니다.")
        }
        
        val feed = feedRepository.findFeedById(id) ?: throw EntityNotFoundException("ID가 ${id}인 피드를 찾을 수 없습니다.")
        val updatedFeed = feed.copy(
            description = request.description,
            updateTime = LocalDateTime.now()
        )
        feedRepository.save(updatedFeed)
    }
}