package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.feed.presentation.dto.request.CreateFeedRequest
import team.twelve.ahu.domain.feed.presentation.repository.FeedRepository
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.presentation.repository.WordRepository
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateFeedService(
    private val feedRepository: FeedRepository,
    private val wordRepository: WordRepository
) {
    @Transactional
    fun execute(request: CreateFeedRequest, wordId: UUID) {
        val word: Word = wordRepository.findWordById(wordId)

        val feed = Feed(
            description = request.description,
            author = request.author,
            createTime = LocalDateTime.now(),
            updateTime = LocalDateTime.now(),
            word = word
        )

        feedRepository.save(feed)
    }
}