package team.twelve.ahu.domain.like.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import team.twelve.ahu.domain.like.presentation.dto.LikeResponse
import team.twelve.ahu.domain.like.service.LikeService
import java.util.*

@Tag(name = "Like", description = "좋아요 API")
@RestController
@RequestMapping("/likes")
class LikeController(
    private val likeService: LikeService
) {

    @Operation(
        summary = "피드 좋아요 등록 또는 취소",
        description = "해당 피드에 대해 좋아요를 등록하거나 취소합니다. (토글 방식)",
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "좋아요 성공"),
        ]
    )

    @PostMapping("/{feedId}")
    fun toggleLike(
        @PathVariable feedId: UUID,
        @RequestHeader("Authorization") token: String,
    ): LikeResponse {
        return likeService.toggleLike(token, feedId)
    }
}
