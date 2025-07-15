package team.twelve.ahu.domain.word.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import team.twelve.ahu.domain.feed.entitiy.Feed
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "word")
class Word(
    @Id
    @Column(name="word_id")
    val id: UUID = UUID.randomUUID(),

    @Column(name= "word")
    val word: String = "",

    @Column(name="description")
    val description: String = "",

    @Column(name = "create_time")
    val createTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_time")
    val updateTime: LocalDateTime = LocalDateTime.now(),


    @OneToMany(mappedBy = "word", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val feeds: List<Feed> = emptyList(),
) {

}