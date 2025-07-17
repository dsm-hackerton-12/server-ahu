package team.twelve.ahu.domain.feed.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.feed.entitiy.repository.FeedRepository
import team.twelve.ahu.domain.feed.service.dto.AiFeedGenerationResult
import team.twelve.ahu.domain.feed.service.openai.OpenAiClient
import team.twelve.ahu.domain.user.entity.User
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.entity.repository.WordRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import java.time.LocalDateTime

@Service
class AiFeedGenerationService(
    private val feedRepository: FeedRepository,
    private val wordRepository: WordRepository,
    private val userRepository: UserRepository,
    private val openAiClient: OpenAiClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val AI_SYSTEM_EMAIL = "ai@ahu.system"

    @Transactional
    fun generateAiFeedFromRandomWord() {
        logger.info("AI 피드 생성 시작")

        val randomWord = wordRepository.findRandomWord()
            ?: throw EntityNotFoundException("랜덤 단어를 찾을 수 없습니다. Word 테이블이 비어있는지 확인해주세요.")

        logger.info("선택된 단어: ${randomWord.word}")

        val aiUser = getOrCreateAiSystemUser()

        try {
            val aiGeneratedContent = openAiClient.generateContent(randomWord.word)
            logger.info("AI 컨텐츠 생성 완료: ${aiGeneratedContent.take(50)}...")

            val aiFeed = Feed(
                description = aiGeneratedContent,
                author = aiUser,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                writtenByAi = true,
                word = randomWord
            )

            feedRepository.save(aiFeed)
            logger.info("AI 피드 저장 완료. Feed ID: ${aiFeed.id}")

        } catch (e: Exception) {
            logger.error("AI 피드 생성 중 오류 발생", e)
            throw e
        }
    }

    @Transactional
    fun getOrCreateAiSystemUser(): User {
        val existingUser = userRepository.findByEmail(AI_SYSTEM_EMAIL)
        if (existingUser != null) {
            return existingUser
        }

        logger.info("AI 시스템 사용자가 존재하지 않음. 새로 생성합니다.")
        val aiUser = User(
            email = AI_SYSTEM_EMAIL,
            name = "AI Assistant"
        )
        
        return userRepository.save(aiUser)
    }

    @Transactional
    fun generateAiFeedsForAllWords(): AiFeedGenerationResult {
        logger.info("모든 워드에 대한 AI 피드 생성 시작")

        val allWords = wordRepository.findAll().toList()
        logger.info("전체 워드 수: ${allWords.size}")

        if (allWords.isEmpty()) {
            logger.warn("생성할 워드가 없습니다.")
            return AiFeedGenerationResult(0, 0, 0)
        }

        val aiUser = getOrCreateAiSystemUser()
        var generatedCount = 0
        var skippedCount = 0

        for (word in allWords) {
            try {
                val hasAiFeed = feedRepository.existsByWordAndWrittenByAi(word, true)
                if (hasAiFeed) {
                    logger.debug("워드 '${word.word}'에 이미 AI 피드가 존재합니다. 건너뜁니다.")
                    skippedCount++
                    continue
                }

                logger.info("워드 '${word.word}'에 대한 AI 피드 생성 중...")
                val aiGeneratedContent = openAiClient.generateContent(word.word)

                val aiFeed = Feed(
                    description = aiGeneratedContent,
                    author = aiUser,
                    createTime = LocalDateTime.now(),
                    updateTime = LocalDateTime.now(),
                    writtenByAi = true,
                    word = word
                )

                feedRepository.save(aiFeed)
                generatedCount++
                logger.info("워드 '${word.word}'에 대한 AI 피드 생성 완료")

                Thread.sleep(1000)
            } catch (e: Exception) {
                logger.error("워드 '${word.word}'에 대한 AI 피드 생성 중 오류 발생: ${e.message}", e)
                skippedCount++
            }
        }

        val result = AiFeedGenerationResult(
            totalWords = allWords.size,
            generatedFeeds = generatedCount,
            skippedWords = skippedCount
        )

        logger.info("모든 워드에 대한 AI 피드 생성 완료 - 전체: ${result.totalWords}, 생성: ${result.generatedFeeds}, 건너뜀: ${result.skippedWords}")
        return result
    }
}