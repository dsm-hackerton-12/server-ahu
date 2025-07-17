package team.twelve.ahu.domain.word.entity.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import team.twelve.ahu.domain.word.entity.Word
import java.util.UUID

interface WordRepository : CrudRepository<Word, UUID> {
    fun findWordById(id: UUID): Word?
    fun findByWord(word: String): Word?
    fun findAllByWord(word: String): List<Word>
    
    @Query("SELECT w FROM word w ORDER BY RAND() LIMIT 1")
    fun findRandomWord(): Word?
}