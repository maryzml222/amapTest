package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.PassPayRequest;
import com.example.mylibrary.domain.model.response.PassPayEntity;

import rx.Observable;

public interface PassPayRepository {
    /**
     * 订单支付
     * @param request
     * @return
     */
    Observable<PassPayEntity> passPay(PassPayRequest request);
}
