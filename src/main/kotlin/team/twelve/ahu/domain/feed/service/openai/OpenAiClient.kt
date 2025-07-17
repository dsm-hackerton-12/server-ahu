package team.twelve.ahu.domain.feed.service.openai

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service
import team.twelve.ahu.global.config.OpenAiConfig
import team.twelve.ahu.global.exception.InternalServerException
import java.io.IOException
import java.util.concurrent.TimeUnit

@Service
class OpenAiClient(
    private val openAiConfig: OpenAiConfig,
    private val objectMapper: ObjectMapper
) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

    fun generateContent(prompt: String): String {
        val request = OpenAiRequest(
            model = openAiConfig.model,
            messages = listOf(
                OpenAiMessage(
                    role = "system",
                    content = "당신은 교육적이고 친근한 AI입니다. 주어진 단어에 대해 자신의 말로 쉽고 재미있게 설명해주세요. 마치 친구에게 이야기하듯이 200자 이내로 한국어로 작성해주세요."
                ),
                OpenAiMessage(
                    role = "user",
                    content = "다음 단어를 내 자신의 말로 쉽고 재미있게 설명해주세요: $prompt"
                )
            )
        )

        val json = objectMapper.writeValueAsString(request)
        val requestBody = json.toRequestBody(mediaType)

        val httpRequest = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer ${openAiConfig.key}")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        try {
            client.newCall(httpRequest).execute().use { response ->
                if (!response.isSuccessful) {
                    throw InternalServerException("OpenAI API 호출 실패: ${response.code} ${response.message}")
                }

                val responseBody = response.body?.string()
                    ?: throw InternalServerException("OpenAI API 응답이 비어있습니다.")

                val openAiResponse = objectMapper.readValue(responseBody, OpenAiResponse::class.java)
                
                return openAiResponse.choices.firstOrNull()?.message?.content
                    ?: throw InternalServerException("OpenAI API 응답에서 콘텐츠를 추출할 수 없습니다.")
            }
        } catch (e: IOException) {
            throw InternalServerException("OpenAI API 호출 중 네트워크 오류가 발생했습니다: ${e.message}")
        }
    }
}