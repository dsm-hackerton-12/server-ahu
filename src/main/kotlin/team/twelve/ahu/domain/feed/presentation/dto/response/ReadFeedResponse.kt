package team.twelve.ahu.domain.feed.presentation.dto.response

import team.twelve.ahu.domain.user.entity.User
import java.time.LocalDateTime
import java.util.UUID

data class ReadFeedResponse(
    val id: UUID,
    val description: String,
    val author: User,
    val createTime: LocalDateTime,
    val updatedTime: LocalDateTime,
)
