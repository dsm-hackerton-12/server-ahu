package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import java.util.UUID

@Service
class ReadFeedService(
    private val feedRepository: FeedRepository,
) {
    @Transactional
    fun execute(id: UUID): ReadFeedResponse {
        val feed = feedRepository.findFeedById(id) ?: throw EntityNotFoundException("ID가 ${id}인 피드를 찾을 수 없습니다.")

        val readFeedResponse = ReadFeedResponse(
            id = feed.id,
            description = feed.description,
            author = feed.author,
            createTime = feed.createTime,
            updatedTime = feed.updateTime,
            word = feed.word.word
        )

         return readFeedResponse
    }
}