package team.twelve.ahu.domain.feed.service

import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.feed.presentation.dto.request.CreateFeedRequest
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.user.entity.User
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.entity.repository.WordRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import team.twelve.ahu.global.exception.InvalidRequestException
import team.twelve.ahu.global.security.jwt.JwtTokenProvider
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateFeedService(
    private val feedRepository: FeedRepository,
    private val wordRepository: WordRepository,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun execute(request: CreateFeedRequest, token: String, wordId: UUID) {
        if (request.description.isBlank()) {
            throw InvalidRequestException("피드 내용은 비어있을 수 없습니다.")
        }

        val cleanToken = if (token.startsWith("Bearer ")) {
            token.substring(7)
        } else {
            token
        }

        val userId = jwtTokenProvider.extractUserId(cleanToken)

        val user: User = userRepository.findUserById(userId)
        val word: Word = wordRepository.findWordById(wordId) ?: throw EntityNotFoundException("ID가 ${wordId}인 단어를 찾을 수 없습니다.")

        val feed = Feed(
            description = request.description,
            author = user,
            createTime = LocalDateTime.now(),
            updateTime = LocalDateTime.now(),
            word = word
        )
        feedRepository.findFeedById(id)
        feedRepository.save(feed)
    }
}