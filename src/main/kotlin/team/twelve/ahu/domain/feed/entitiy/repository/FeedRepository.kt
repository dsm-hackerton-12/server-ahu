package team.twelve.ahu.domain.feed.entitiy.repository

import org.springframework.data.repository.CrudRepository
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.word.entity.Word
import java.util.UUID

interface FeedRepository : CrudRepository<Feed, UUID> {
    fun findFeedById(id: UUID): Feed?
    fun findAllByWord(word: Word): List<Feed>
    fun save(feed: Feed)
}