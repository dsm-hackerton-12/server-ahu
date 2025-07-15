package team.twelve.ahu.domain.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.feed.presentation.dto.request.CreateFeedRequest
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.presentation.repository.WordRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import team.twelve.ahu.global.exception.InvalidRequestException
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateFeedService(
    private val feedRepository: FeedRepository,
    private val wordRepository: WordRepository
) {
    @Transactional
    fun execute(request: CreateFeedRequest, id: UUID) {
        if (request.description.isBlank()) {
            throw InvalidRequestException("피드 내용은 비어있을 수 없습니다.")
        }
        if (request.author.isBlank()) {
            throw InvalidRequestException("작성자는 비어있을 수 없습니다.")
        }
        
        val word: Word = wordRepository.findWordById(id) ?: throw EntityNotFoundException("ID가 ${id}인 단어를 찾을 수 없습니다.")

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