package team.twelve.ahu.domain.word.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverEncycResponse(
    @JsonProperty("lastBuildDate")
    val lastBuildDate: String,
    @JsonProperty("total")
    val total: Int,
    @JsonProperty("start")
    val start: Int,
    @JsonProperty("display")
    val display: Int,
    @JsonProperty("items")
    val items: List<NaverEncycItem>
)

data class NaverEncycItem(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("link")
    val link: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("thumbnail")
    val thumbnail: String?
)