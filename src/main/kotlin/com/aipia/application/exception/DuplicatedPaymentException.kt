package com.aipia.application.exception

import com.aipia._common.exception.BusinessException

class DuplicatedPaymentException(message: String = "이미 처리된 주문 결제입니다.") :
    BusinessException(409, message)