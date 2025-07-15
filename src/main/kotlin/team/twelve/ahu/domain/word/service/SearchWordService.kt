package team.twelve.ahu.domain.word.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.entity.repository.WordRepository
import team.twelve.ahu.domain.word.presentation.feign.NaverEncycClient
import team.twelve.ahu.global.config.NaverApiConfig
import team.twelve.ahu.global.exception.EntityNotFoundException
import team.twelve.ahu.global.exception.InternalServerException
import team.twelve.ahu.global.exception.InvalidRequestException
import java.time.LocalDateTime
import java.util.UUID

@Service
class SearchWordService(
    private val naverEncycClient: NaverEncycClient,
    private val naverApiConfig: NaverApiConfig,
    private val wordRepository: WordRepository
) {
    
    @Transactional
    fun execute(query: String): List<Word> {
        if (query.isBlank()) {
            throw InvalidRequestException("검색어는 비어있을 수 없습니다.")
        }
        
        val existingWords = wordRepository.findAllByWord(query)
        if (existingWords.isNotEmpty()) {
            return existingWords
        }
        
        try {
            val response = naverEncycClient.searchEncyclopedia(
                clientId = naverApiConfig.clientId,
                clientSecret = naverApiConfig.clientSecret,
                query = query,
                display = 10
            )
            
            if (response.items.isEmpty()) {
                throw EntityNotFoundException("'${query}'에 대한 백과사전 정보를 찾을 수 없습니다.")
            }
            
            val words = response.items.map { item ->
                val description = item.description
                    .replace("<[^>]*>".toRegex(), "")
                    .replace("&quot;", "\"")
                    .replace("&amp;", "&")
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                
                Word(
                    id = UUID.randomUUID(),
                    word = query,
                    description = description,
                    createTime = LocalDateTime.now(),
                    updateTime = LocalDateTime.now()
                )
            }
            
            return wordRepository.saveAll(words).toList()
            
        } catch (e: Exception) {
            throw InternalServerException("네이버 API 호출 중 오류가 발생했습니다: ${e.message}")
        }
    }
}