package team.twelve.ahu.domain.feed.presentation.repository

import org.springframework.data.repository.CrudRepository
import team.twelve.ahu.domain.feed.entitiy.Feed
import java.util.UUID

interface FeedRepository : CrudRepository<Feed, UUID> {
    fun findFeedById(id: UUID): Feed?
    fun save(feed: Feed)
}