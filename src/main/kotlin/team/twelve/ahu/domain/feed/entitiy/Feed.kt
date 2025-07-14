package team.twelve.ahu.domain.feed.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import team.twelve.ahu.domain.word.entity.Word
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "feed")
class Feed(
    @Id
    @Column(name="feed_id")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "description")
    val description: String,

    @Column(name = "author")
    val author: String,

    @Column(name = "create_time")
    val createTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_time")
    val updateTime: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "word_id")
    val word: Word? = null,
) {

}