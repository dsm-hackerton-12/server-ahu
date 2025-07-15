package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadAllFeedResponse
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.word.entity.repository.WordRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import java.util.UUID

@Service
class ReadAllFeedService(
    private val feedRepository: FeedRepository,
    private val wordRepository: WordRepository,
) {
    @Transactional
    fun execute(id: UUID): ReadAllFeedResponse {
        val word = wordRepository.findWordById(id) ?: throw EntityNotFoundException("ID가 ${id}인 단어를 찾을 수 없습니다.")
        val feeds = feedRepository.findAllByWord(word)
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