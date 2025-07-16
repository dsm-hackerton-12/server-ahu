package team.twelve.ahu.domain.like.entity.repository

import org.springframework.data.repository.CrudRepository
import team.twelve.ahu.domain.feed.entitiy.Feed
import team.twelve.ahu.domain.like.entity.Like
import team.twelve.ahu.domain.user.entity.User

interface LikeRepository : CrudRepository<Like, Long> {
    fun findByUserAndFeed(user: User, feed: Feed): Like?  // 추가 필요
}
