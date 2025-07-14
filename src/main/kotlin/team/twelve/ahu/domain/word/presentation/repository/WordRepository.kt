package team.twelve.ahu.domain.word.presentation.repository

import org.springframework.data.repository.CrudRepository
import team.twelve.ahu.domain.word.entity.Word
import java.util.UUID

interface WordRepository : CrudRepository<Word, UUID> {
    fun findWordById(id: UUID): Word
}