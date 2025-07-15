package team.twelve.ahu.domain.word.presentation.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.service.SearchWordService

@RestController
@RequestMapping("/api/word")
class WordController(
    private val searchWordService: SearchWordService
) {
    
    @GetMapping("/search")
    fun searchWord(@RequestParam query: String): List<Word> {
        return searchWordService.execute(query)
    }
}