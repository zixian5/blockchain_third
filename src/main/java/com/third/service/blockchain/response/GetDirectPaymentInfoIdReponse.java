package com.third.service.blockchain.response;

import com.google.gson.Gson;
import com.third.service.blockchain.entity.Payment;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetDirectPaymentInfoIdReponse extends AbstractResponse{
    Payment payment = null;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public String toJSonString()
    {
        Map<String ,Object> map = new LinkedHashMap<>();
        map.put("code",ResponseCode.SUCCESS.getCode());
        map.put("data",payment);
        return new Gson().toJson(map);
    }
}
