package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import java.util.UUID

@Service
class DeleteFeedService(
    private val feedRepository: FeedRepository,
) {
    @Transactional
    fun execute(id: UUID) {
        if (!feedRepository.existsById(id)) {
            throw EntityNotFoundException("ID가 ${id}인 피드를 찾을 수 없습니다.")
        }
        feedRepository.deleteById(id)
    }
}