package team.twelve.ahu.domain.user.presentation

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
class UserController(
    private val userService: UserService,
    private val updateNameService: UpdateNameService
) {

    @PatchMapping("/nickname")
    fun updateName(
        @RequestBody request: UpdateNameRequest,
        authentication: Authentication
    ){
        val userId = UUID.fromString(authentication.name)
        updateNameService.updateName(userId, request.name)
    }
}