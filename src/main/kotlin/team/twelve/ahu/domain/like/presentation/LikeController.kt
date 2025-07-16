package team.twelve.ahu.domain.like.presentation

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import team.twelve.ahu.domain.like.presentation.dto.LikeResponse
import team.twelve.ahu.domain.like.service.LikeService
import java.util.*


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
