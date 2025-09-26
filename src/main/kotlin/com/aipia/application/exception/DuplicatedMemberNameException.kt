package com.aipia.application.exception

import com.aipia._common.exception.BusinessException

class DuplicatedMemberNameException(message: String = "이미 존재하는 아이디입니다.") :
    BusinessException(409, message)