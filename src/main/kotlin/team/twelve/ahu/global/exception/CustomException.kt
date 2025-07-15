package team.twelve.ahu.global.exception

import org.springframework.http.HttpStatus

open class CustomException(
    val httpStatus: HttpStatus,
    override val message: String
) : RuntimeException(message)

class EntityNotFoundException(
    message: String = "요청한 리소스를 찾을 수 없습니다."
) : CustomException(HttpStatus.NOT_FOUND, message)

class InvalidRequestException(
    message: String = "잘못된 요청입니다."
) : CustomException(HttpStatus.BAD_REQUEST, message)

class UnauthorizedException(
    message: String = "인증이 필요합니다."
) : CustomException(HttpStatus.UNAUTHORIZED, message)

class ForbiddenException(
    message: String = "접근 권한이 없습니다."
) : CustomException(HttpStatus.FORBIDDEN, message)

class InternalServerException(
    message: String = "서버 내부 오류가 발생했습니다."
) : CustomException(HttpStatus.INTERNAL_SERVER_ERROR, message)