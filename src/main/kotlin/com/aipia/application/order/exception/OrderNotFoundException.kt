package com.aipia.application.order.exception

import com.aipia._common.exception.BusinessException

class OrderNotFoundException(message: String = "존재하지 않는 주문입니다.") :
    BusinessException(404, message)