package com.aipia.application.member.exception

import com.aipia._common.exception.BusinessException

class InvalidPasswordException(message: String = "비밀번호가 올바르지 않습니다.") :
    BusinessException(401, message)