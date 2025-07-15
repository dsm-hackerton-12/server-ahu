package team.twelve.ahu.domain.feed.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import org.jetbrains.annotations.NotNull
import team.twelve.ahu.domain.user.entity.User
import team.twelve.ahu.domain.word.entity.Word
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "feed")
data class Feed(
    @Id
    @Column(name="feed_id")
    @NotNull
    val id: UUID = UUID.randomUUID(),

    @Column(name = "description")
    @NotNull
    val description: String = "",

    @Column(name = "author")
    @ManyToOne
    @NotNull
    val author: User,

    @Column(name = "create_time")
    @NotNull
    val createTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_time")
    @NotNull
    val updateTime: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @NotNull
    @JoinColumn(name = "word_id")
    val word: Word = Word()
) {

}