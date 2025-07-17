package team.twelve.ahu.domain.word.presentation.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.word.entity.Word
import team.twelve.ahu.domain.word.service.ReadWordService
import team.twelve.ahu.domain.word.service.SearchWordService
import java.util.UUID

@RestController
@RequestMapping("/api/word")
@Tag(name = "Word", description = "단어 검색 API")
class WordController(
    private val searchWordService: SearchWordService,
    private val readWordService: ReadWordService
) {

    @GetMapping("/search")
    @Operation(summary = "단어 검색", description = "네이버 지식백과에서 단어를 검색합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공적으로 단어 검색"),
        ApiResponse(responseCode = "400", description = "잘못된 요청")
    ])
    fun searchWord(@Parameter(description = "검색할 단어") @RequestParam query: String): List<Word> {
        return searchWordService.execute(query)
    }

    @GetMapping("/{wordId}")
    @Operation(summary = "단어 조회", description = "특정 단어를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "성공적으로 조회됨"),
            ApiResponse(responseCode = "404", description = "단어를 찾을 수 없음")
        ]
    )
    fun getWordById(@Parameter(description = "단어 ID") @PathVariable wordId: UUID): Word {
        return readWordService.findWordById(wordId)
    }
}