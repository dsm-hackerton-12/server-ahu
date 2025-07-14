package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadAllFeedResponse
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.feed.presentation.repository.FeedRepository
import team.twelve.ahu.domain.word.presentation.repository.WordRepository
import java.util.UUID

@Service
class ReadAllFeedService(
    private val feedRepository: FeedRepository,
    private val wordRepository: WordRepository,
) {
    @Transactional
    fun execute(id: UUID): ReadAllFeedResponse {
        val word = wordRepository.findWordById(id)
        val feedList = feedRepository.findAllByWord(word)
        val readFeedResponseList = feedList.map { feed ->
            ReadFeedResponse(
                id = feed.id,
                description = feed.description,
                author = feed.author,
                createTime = feed.createTime,
                updatedTime = feed.updateTime
            )
        }

        return ReadAllFeedResponse(
            feedList = readFeedResponseList
        )
    }
}