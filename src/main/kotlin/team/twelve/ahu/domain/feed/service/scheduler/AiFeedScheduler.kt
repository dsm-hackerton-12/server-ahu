package team.twelve.ahu.domain.feed.service.scheduler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import team.twelve.ahu.domain.feed.service.AiFeedGenerationService

@Component
class AiFeedScheduler(
    private val aiFeedGenerationService: AiFeedGenerationService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(cron = "0 0 9 * * ?")
    fun generateDailyAiFeed() {
        logger.info("매일 오전 9시 전체 워드 AI 피드 생성 작업 시작")
        
        try {
            val result = aiFeedGenerationService.generateAiFeedsForAllWords()
            logger.info("AI 피드 생성 작업 완료 - 전체: ${result.totalWords}, 생성: ${result.generatedFeeds}, 건너뜀: ${result.skippedWords}")
        } catch (e: Exception) {
            logger.error("AI 피드 생성 중 오류 발생: ${e.message}", e)
        }
    }

    @Scheduled(cron = "0 0 21 * * ?")
    fun generateEveningAiFeed() {
        logger.info("매일 오후 9시 전체 워드 AI 피드 생성 작업 시작")
        
        try {
            val result = aiFeedGenerationService.generateAiFeedsForAllWords()
            logger.info("AI 피드 생성 작업 완료 - 전체: ${result.totalWords}, 생성: ${result.generatedFeeds}, 건너뜀: ${result.skippedWords}")
        } catch (e: Exception) {
            logger.error("AI 피드 생성 중 오류 발생: ${e.message}", e)
        }
    }
}