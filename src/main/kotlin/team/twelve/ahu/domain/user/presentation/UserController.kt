package team.twelve.ahu.domain.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.twelve.ahu.domain.auth.service.UserService
import team.twelve.ahu.domain.user.presentation.dto.request.UpdateNameRequest
import team.twelve.ahu.domain.user.service.UpdateNameService
import java.util.UUID

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "사용자 관련 API")
class UserController(
    private val userService: UserService,
    private val updateNameService: UpdateNameService
) {

    @PatchMapping("/nickname")
    @Operation(summary = "사용자 닉네임 변경", description = "사용자의 닉네임을 변경합니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공적으로 닉네임 변경"),
        ApiResponse(responseCode = "400", description = "잘못된 요청"),
        ApiResponse(responseCode = "401", description = "인증 실패")
    ])
    fun updateName(
        @RequestBody request: UpdateNameRequest,
        authentication: Authentication
    ){
        val userId = UUID.fromString(authentication.name)
        updateNameService.updateName(userId, request.name)
    }
}