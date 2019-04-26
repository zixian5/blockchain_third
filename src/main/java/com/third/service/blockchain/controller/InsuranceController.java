package com.third.service.blockchain.controller;


import com.third.service.blockchain.entity.Insurance;
import com.third.service.blockchain.repository.InsuranceRepository;
import com.third.service.blockchain.response.GetInsuranceListResponse;
import com.third.service.blockchain.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InsuranceController {
    @Autowired
    private InsuranceRepository insuranceRepository;



    @RequestMapping("/insuranceList/getInsuranceList")
    @ResponseBody
    public String getInsuranceList()
    {
        List<Insurance> insuranceList = insuranceRepository.findAll();
        GetInsuranceListResponse response = new GetInsuranceListResponse();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setInsuranceList(insuranceList);
        return response.getJsonString();
    }



}
