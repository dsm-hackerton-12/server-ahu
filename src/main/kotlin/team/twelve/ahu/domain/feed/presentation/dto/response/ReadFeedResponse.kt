package team.twelve.ahu.domain.feed.presentation.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class ReadFeedResponse(
    val id: UUID,
    val description: String,
    val author: String,
    val createTime: LocalDateTime,
    val updatedTime: LocalDateTime,
)
