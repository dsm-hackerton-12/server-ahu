package team.twelve.ahu.domain.word.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.entity.repository.WordRepository
import team.twelve.ahu.global.exception.EntityNotFoundException
import java.util.UUID

@Service
class ReadWordService(
    private val wordRepository: WordRepository
) {
    @Transactional(readOnly = true)
    fun findWordById(wordId: UUID): Word {
        return wordRepository.findById(wordId).orElseThrow {
            EntityNotFoundException("ID가 ${wordId}인 단어를 찾을 수 없습니다.")
        }
    }
}
