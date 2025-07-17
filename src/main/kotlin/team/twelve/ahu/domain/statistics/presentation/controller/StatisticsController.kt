package team.twelve.ahu.domain.statistics.presentation.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.statistics.presentation.dto.response.KeywordStatisticsResponse
import team.twelve.ahu.domain.statistics.presentation.dto.response.OverallStatisticsResponse
import team.twelve.ahu.domain.statistics.service.StatisticsService

@RestController
@RequestMapping("/api/statistics")
@Tag(name = "Statistics", description = "AI vs Human 포스트 통계 API")
class StatisticsController(
    private val statisticsService: StatisticsService
) {

    @GetMapping("/keyword")
    @Operation(
        summary = "특정 키워드에 대한 AI vs 인간 포스트 좋아요 통계",
        description = "특정 키워드에 대해 AI가 작성한 글과 인간이 작성한 글의 좋아요 통계를 비교합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "통계 조회 성공",
        content = [Content(schema = Schema(implementation = KeywordStatisticsResponse::class))]
    )
    @ApiResponse(responseCode = "400", description = "키워드를 찾을 수 없음")
    fun getKeywordStatistics(
        @Parameter(description = "통계를 조회할 키워드", required = true)
        @RequestParam keyword: String
    ): ResponseEntity<KeywordStatisticsResponse> {
        return try {
            val statistics = statisticsService.getKeywordStatistics(keyword)
            ResponseEntity.ok(statistics)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/overall")
    @Operation(
        summary = "전체 AI vs 인간 포스트 좋아요 통계",
        description = "전체적으로 AI가 작성한 글과 인간이 작성한 글의 좋아요 통계를 비교합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "통계 조회 성공",
        content = [Content(schema = Schema(implementation = OverallStatisticsResponse::class))]
    )
    fun getOverallStatistics(): ResponseEntity<OverallStatisticsResponse> {
        val statistics = statisticsService.getOverallStatistics()
        return ResponseEntity.ok(statistics)
    }
}