package com.aipia.application.exception

import com.aipia._common.exception.BusinessException

class ProductNotFoundException(message: String = "존재하지 않는 상품입니다.") :
    BusinessException(404, message)