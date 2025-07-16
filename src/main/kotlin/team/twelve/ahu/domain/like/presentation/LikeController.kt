package team.twelve.ahu.domain.like.presentation

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

    @PostMapping("/{feedId}")
    fun toggleLike(
        @PathVariable feedId: UUID,
        @RequestHeader("Authorization") token: String,
    ): LikeResponse {
        return likeService.toggleLike(token, feedId)
    }
}
