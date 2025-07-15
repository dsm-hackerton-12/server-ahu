package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import team.twelve.ahu.domain.feed.presentation.repository.FeedRepository
import java.util.UUID

@Service
class DeleteFeedService(
    private val feedRepository: FeedRepository,
) {
    @Transactional
    fun execute(@PathVariable id: UUID) {
        feedRepository.deleteById(id)
    }
}