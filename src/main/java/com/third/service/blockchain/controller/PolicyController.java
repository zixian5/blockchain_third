package com.third.service.blockchain.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.third.service.blockchain.entity.Policy;
import com.third.service.blockchain.entity.User;
import com.third.service.blockchain.repository.PolicyRepository;
import com.third.service.blockchain.repository.UserRepository;
import com.third.service.blockchain.response.GetInsurancePurchasingInfoListResponse;
import com.third.service.blockchain.response.ResponseCode;
import com.third.service.blockchain.service.PolicyService;
import com.third.service.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PolicyController {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PolicyService policyService;

    @RequestMapping("/insurancePurchasingProcess/getInsurancePurchasingInfoList")
    @ResponseBody
    public String getInsurancePurchasingInfoList(String email)
    {
        User user = userRepository.findBYEmail(email);
        try {
            policyService.smellPolicy(user.getPubkey(),user.getPrikey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Policy> policies = policyRepository.findAllByPublicKey(user.getPubkey());
        GetInsurancePurchasingInfoListResponse getInsurancePurchasingInfoListResponse = new GetInsurancePurchasingInfoListResponse();
        getInsurancePurchasingInfoListResponse.setCode(ResponseCode.SUCCESS.getCode());
        getInsurancePurchasingInfoListResponse.setPolicies(policies);
        return getInsurancePurchasingInfoListResponse.getJsonStrig();
    }

    @ResponseBody
    @RequestMapping("/insurancePurchasingDetail/getInsurancePurchasingInfo")
    public String getInsurancePurchasingInfo(String insurancePurchasingInfoId,String email)
    {
        Policy policy = policyRepository.findByInsurancePurchasingInfoId(insurancePurchasingInfoId);

        if(policy == null)
        {
            User user = userRepository.findBYEmail(email);
            try {
                policyService.smellPolicy(user.getPubkey(),user.getPrikey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        policy = policyRepository.findByInsurancePurchasingInfoId(insurancePurchasingInfoId);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("code", ResponseCode.SUCCESS.getCode());
        map.put("data",policy);
        return new Gson().toJson(map);
    }


}
