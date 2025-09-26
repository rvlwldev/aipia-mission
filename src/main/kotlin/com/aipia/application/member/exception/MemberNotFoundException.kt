package com.aipia.application.member.exception

import com.aipia._common.exception.BusinessException

class MemberNotFoundException(message: String = "존재하지 않는 사용자입니다.") :
    BusinessException(404, message)