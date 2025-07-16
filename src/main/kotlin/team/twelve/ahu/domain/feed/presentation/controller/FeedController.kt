package team.twelve.ahu.domain.feed.presentation.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.feed.presentation.dto.request.CreateFeedRequest
import team.twelve.ahu.domain.feed.presentation.dto.request.UpdateFeedRequest
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadAllFeedResponse
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.feed.service.CreateFeedService
import team.twelve.ahu.domain.feed.service.DeleteFeedService
import team.twelve.ahu.domain.feed.service.ReadAllFeedService
import team.twelve.ahu.domain.feed.service.ReadAllService
import team.twelve.ahu.domain.feed.service.ReadFeedService
import team.twelve.ahu.domain.feed.service.UpdateFeedService
import team.twelve.ahu.domain.user.entity.User
import java.util.UUID

@RestController
@RequestMapping("/api/feed")
@Tag(name = "Feed", description = "피드 관련 API")
class FeedController(
    private val createFeedService: CreateFeedService,
    private val readFeedService: ReadFeedService,
    private val readAllFeedService: ReadAllFeedService,
    private val readAllService: ReadAllService,
    private val updateFeedService: UpdateFeedService,
    private val deleteFeedService: DeleteFeedService
) {
    @GetMapping
    @Operation(summary = "전체 피드 조회", description = "모든 피드를 조회합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공적으로 조회됨")
    ])
    fun findAll() : ReadAllFeedResponse {
        return readAllService.readAllFeed()
    }

    @GetMapping("/{id}")
    @Operation(summary = "피드 조회", description = "특정 피드를 조회합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
        ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음")
    ])
    fun findFeed(@Parameter(description = "피드 ID") @PathVariable id: UUID): ReadFeedResponse {
        return readFeedService.execute(id)
    }

    @GetMapping("/{id}/all")
    @Operation(summary = "사용자의 모든 피드 조회", description = "특정 사용자의 모든 피드를 조회합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
        ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    ])
    fun findFeedAll(@Parameter(description = "사용자 ID") @PathVariable id: UUID): ReadAllFeedResponse {
        return readAllFeedService.execute(id)
    }

    @PostMapping("/{wordId}")
    @Operation(summary = "피드 생성", description = "새로운 피드를 생성합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "성공적으로 생성됨"),
        ApiResponse(responseCode = "400", description = "잘못된 요청")
    ])
    fun createFeed(
        @Parameter(description = "워드 ID") @PathVariable wordId: UUID,
        @RequestHeader("Authorization") token: String,
        @RequestBody request: CreateFeedRequest
    ) {
        createFeedService.execute(request, token, wordId)
    }

    @PatchMapping("/{id}")
    @Operation(summary = "피드 수정", description = "기존 피드를 수정합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공적으로 수정됨"),
        ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음")
    ])
    fun update(@Parameter(description = "피드 ID") @PathVariable id: UUID, @RequestBody request: UpdateFeedRequest) {
        updateFeedService.execute(id, request)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "피드 삭제", description = "기존 피드를 삭제합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "성공적으로 삭제됨"),
        ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음")
    ])
    fun delete(@Parameter(description = "피드 ID") @PathVariable id: UUID) {
        deleteFeedService.execute(id)
    }
}