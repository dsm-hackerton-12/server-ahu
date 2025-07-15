package team.twelve.ahu.domain.word.presentation.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import team.twelve.ahu.domain.word.presentation.dto.NaverEncycResponse

@FeignClient(
    name = "naver-encyc-client",
    url = "https://openapi.naver.com"
)
interface NaverEncycClient {

    @GetMapping("/v1/search/encyc.json")
    fun searchEncyclopedia(
        @RequestHeader("X-Naver-Client-Id") clientId: String,
        @RequestHeader("X-Naver-Client-Secret") clientSecret: String,
        @RequestParam("query") query: String,
        @RequestParam("display") display: Int = 10,
        @RequestParam("start") start: Int = 1
    ): NaverEncycResponse
}