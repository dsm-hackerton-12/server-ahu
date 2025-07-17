package team.twelve.ahu.domain.feed.presentation.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.feed.service.AiFeedGenerationService

@RestController
@RequestMapping("/api/ai-feeds")
@Tag(name = "AI Feed", description = "AI가 생성하는 피드 관련 API")
class AiFeedController(
    private val aiFeedGenerationService: AiFeedGenerationService
) {

    @PostMapping("/generate-random")
    @Operation(
        summary = "랜덤 워드로 AI 피드 생성",
        description = "랜덤하게 선택된 단어를 기반으로 AI가 피드를 생성합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "AI 피드 생성 성공"),
            ApiResponse(responseCode = "404", description = "랜덤 단어를 찾을 수 없음"),
            ApiResponse(responseCode = "500", description = "AI 피드 생성 중 오류 발생")
        ]
    )
    fun generateRandomAiFeed(): ResponseEntity<Map<String, String>> {
        return try {
            aiFeedGenerationService.generateAiFeedFromRandomWord()
            ResponseEntity.ok(mapOf("message" to "AI 피드가 성공적으로 생성되었습니다."))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(mapOf("error" to "AI 피드 생성 중 오류가 발생했습니다: ${e.message}"))
        }
    }

    @PostMapping("/generate-all")
    @Operation(
        summary = "모든 워드에 대한 AI 피드 생성",
        description = "아직 AI 피드가 없는 모든 단어에 대해 AI가 피드를 생성합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "AI 피드 일괄 생성 성공"),
            ApiResponse(responseCode = "500", description = "AI 피드 생성 중 오류 발생")
        ]
    )
    fun generateAllAiFeeds(): ResponseEntity<Map<String, Any>> {
        return try {
            val result = aiFeedGenerationService.generateAiFeedsForAllWords()
            ResponseEntity.ok(mapOf(
                "message" to "AI 피드 일괄 생성이 완료되었습니다.",
                "totalWords" to result.totalWords,
                "generatedFeeds" to result.generatedFeeds,
                "skippedWords" to result.skippedWords
            ))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(mapOf("error" to "AI 피드 일괄 생성 중 오류가 발생했습니다: ${e.message}"))
        }
    }
}