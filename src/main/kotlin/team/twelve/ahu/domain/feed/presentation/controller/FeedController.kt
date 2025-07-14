package team.twelve.ahu.domain.feed.presentation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.feed.presentation.dto.request.CreateFeedRequest
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadAllFeedResponse
import team.twelve.ahu.domain.feed.presentation.dto.response.ReadFeedResponse
import team.twelve.ahu.domain.feed.service.CreateFeedService
import team.twelve.ahu.domain.feed.service.ReadAllFeedService
import team.twelve.ahu.domain.feed.service.ReadFeedService
import java.util.UUID

@RestController
@RequestMapping("/api/feed")
class FeedController(
    private val createFeedService: CreateFeedService,
    private val readFeedService: ReadFeedService,
    private val readAllFeedService: ReadAllFeedService,
) {
    @GetMapping
    fun findAll() {

    }

    @GetMapping("/{id}")
    fun findFeed(@PathVariable id: UUID): ReadFeedResponse {
        return readFeedService.execute(id)
    }

    @GetMapping("/{id}/all")
    fun findFeedAll(@PathVariable id: UUID): ReadAllFeedResponse {
        return readAllFeedService.execute(id)
    }


    @PostMapping("/{id}")
    fun createFeed(@PathVariable id: UUID, @RequestBody request: CreateFeedRequest) {
        createFeedService.execute(request, id)
    }
}