package team.twelve.ahu.domain.feed.presentation.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.feed.presentation.dto.request.CreateFeedRequest
import team.twelve.ahu.domain.feed.service.CreateFeedService
import java.util.UUID

@RestController
@RequestMapping("/api/feed")
class FeedController(
    private val createFeedService: CreateFeedService
) {
    @PostMapping("/{id}")
    fun createFeed(@PathVariable id: UUID, @RequestBody request: CreateFeedRequest) {
        createFeedService.execute(request, id)
    }
}