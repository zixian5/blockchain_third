package com.third.service.blockchain.response;

import com.google.gson.Gson;
import com.third.service.blockchain.entity.Record;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetMedicalRecordInfoListResponse extends AbstractResponse {

    List<Record> records;

    public String toJsonString()
    {
        Map<String ,Object> data = new LinkedHashMap<>();
        data.put("medicalRecordInfoList",records);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("code",code);
        map.put("data",data);
        return new Gson().toJson(map);
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
