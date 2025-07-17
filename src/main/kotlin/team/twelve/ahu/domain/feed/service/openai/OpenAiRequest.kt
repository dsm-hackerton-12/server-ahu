package team.twelve.ahu.domain.feed.service.openai

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiRequest(
    val model: String,
    val messages: List<OpenAiMessage>,
    @JsonProperty("max_tokens")
    val maxTokens: Int = 300,
    val temperature: Double = 0.7
)

data class OpenAiMessage(
    val role: String,
    val content: String
)

data class OpenAiResponse(
    val id: String,
    val choices: List<OpenAiChoice>
)

data class OpenAiChoice(
    val message: OpenAiMessage,
    @JsonProperty("finish_reason")
    val finishReason: String
)