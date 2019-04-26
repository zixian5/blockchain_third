package com.third.service.blockchain.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.third.service.blockchain.entity.Record;
import com.third.service.blockchain.entity.User;
import com.third.service.blockchain.repository.RecordRepository;
import com.third.service.blockchain.repository.UserRepository;
import com.third.service.blockchain.response.GetMedicalRecordInfoListResponse;
import com.third.service.blockchain.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RecordController {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/personalCenter/getMedicalRecordInfoList")
    @ResponseBody
    public String getMedicalRecordInfoList(String email)
    {
        User user = userRepository.findBYEmail(email);
        String pubkey = user !=null ? user.getPubkey() : null;
        List<Record> records = recordRepository.findByPubkey(pubkey);

        GetMedicalRecordInfoListResponse response = new GetMedicalRecordInfoListResponse();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setRecords(records);

        return response.toJsonString();
    }

    @ResponseBody
    @RequestMapping("/personalCenter/authorizationMedicalRecord")
    public String authorizationMedicalRecord(String publicKey)
    {
      //  System.out.println("-----------------"+json+"--------------");
     //   JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
     //   String username = jsonObject.get("publicKey").getAsString();

        //no shixian now

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("code",ResponseCode.SUCCESS.getCode());
        map.put("data",null);

        return new Gson().toJson(map);
    }
}
