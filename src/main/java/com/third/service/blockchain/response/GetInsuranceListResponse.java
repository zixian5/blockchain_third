package com.third.service.blockchain.response;

import com.google.gson.Gson;
import com.third.service.blockchain.entity.Insurance;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetInsuranceListResponse extends AbstractResponse {
    private List<Insurance> insuranceList;

    public List<Insurance> getInsuranceList() {
        return insuranceList;
    }

    public void setInsuranceList(List<Insurance> insuranceList) {
        this.insuranceList = insuranceList;
    }

    public  String getJsonString()
    {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("code",code);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("insuranceList", insuranceList);
        map.put("data",data);
        return new Gson().toJson(map);
    }
}
