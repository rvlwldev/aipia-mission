package com.aipia.application.exception

import com.aipia._common.exception.BusinessException

class MemberNotFoundException(message: String = "사용자를 찾을 수 없습니다.") :
    BusinessException(404, message)