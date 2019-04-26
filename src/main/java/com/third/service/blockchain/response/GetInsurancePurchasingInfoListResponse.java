package com.third.service.blockchain.response;

import com.google.gson.Gson;
import com.third.service.blockchain.entity.Policy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetInsurancePurchasingInfoListResponse extends  AbstractResponse {
    private List<Policy> policies;

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
    public String getJsonStrig()
    {
        Map<String,Object> map =new LinkedHashMap<>();
        map.put("code",code);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("insurancePurchasingInfoList", policies);
        map.put("data",data);
        return new Gson().toJson(map);
    }
}
