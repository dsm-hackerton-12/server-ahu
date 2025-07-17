package team.twelve.ahu.global.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "openai.api")
data class OpenAiConfig(
    var key: String = "",
    var model: String = "gpt-3.5-turbo"
)