package team.twelve.ahu.domain.feed.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import team.twelve.ahu.domain.user.entity.User
import java.time.LocalDateTime
import java.util.UUID

data class ReadFeedResponse(
    @Schema(description = "피드 ID", example = "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6")
    val id: UUID,
    @Schema(description = "피드 내용", example = "오늘 하루는...")
    val description: String,
    @Schema(description = "작성자 정보")
    val author: User,
    @Schema(description = "생성 시간", example = "2023-07-17T10:00:00")
    val createTime: LocalDateTime,
    @Schema(description = "수정 시간", example = "2023-07-17T10:00:00")
    val updatedTime: LocalDateTime,
    @Schema(description = "연관 단어", example = "행복")
    val word: String,
)
