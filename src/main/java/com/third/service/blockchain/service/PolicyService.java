package com.third.service.blockchain.service;

import com.bubi.connect.ContractConnect;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.third.service.blockchain.entity.Insurance;
import com.third.service.blockchain.entity.Payment;
import com.third.service.blockchain.entity.Policy;
import com.third.service.blockchain.repository.InsuranceRepository;
import com.third.service.blockchain.repository.PaymentRepository;
import com.third.service.blockchain.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;

import static cn.xjfme.encrypt.test.SecurityTestAll.SM2Dec;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Resource
    private Environment environment;

    //获取保险
    public  void smellPolicy(String pubKey,String priKey) throws Exception
    {
        String contract = environment.getProperty("insurance.contract");
       // String contract = "a2ef17f2cef9f3df9dab417fa64c7e4854c3509a0b3aee0543efaaec814ba705";
        String json = ContractConnect.get(pubKey,contract);
        System.out.println(json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = object.get("result").getAsJsonObject();
        if(result.get("value").getAsString().equals("false"))
        {
            return;
        }
        String[] encValues = result.get("value").getAsString().split(",");

        for(String encValue : encValues)
        {
            String value = SM2Dec(priKey,encValue);
            System.out.println(value);
            handlePolicyMessage(value);
        }
    }

    private  void handlePolicyMessage(String value)
    {
        JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
        //sign no shixian
        String insuranceRawString = jsonObject.getAsJsonObject("data").get("insurance").toString();
        Insurance insurance = new Gson().fromJson(insuranceRawString,Insurance.class);
        System.out.println(insurance.toString());
        String policyRawString = jsonObject.getAsJsonObject("data").getAsJsonObject().get("policy").toString();
        Policy policy = new Gson().fromJson(policyRawString,Policy.class);
        System.out.println(policy.toString());

        String publicKey = jsonObject.get("senderpubkey").getAsString();
        System.out.println("senderpubkey:  "+publicKey);
        String sign = jsonObject.get("sign").getAsString();
        System.out.println("sign:  "+sign);

        if(insuranceRepository.findByInsuranceId(insurance.getInsuranceId()) == null)
        {
            insuranceRepository.saveAndFlush(insurance);
        }

        if (policyRepository.findByInsurancePurchasingInfoId(policy.getInsurancePurchasingInfoId())==null)
        {
            policyRepository.saveAndFlush(policy);
        }

    }



    public static void main(String[] args) throws  Exception{
       // System.out.println(smellPolicy("042e36e1a0b8329167533551183630e7c24deab17aed97f58e392a34a7f33efe97a64b3c73dbc75583c48d34dbb8b045cfabd8fc21fc40599c22a3c6dcbf132faf"));
       // smellPolicy("042e36e1a0b8329167533551183630e7c24deab17aed97f58e392a34a7f33efe97a64b3c73dbc75583c48d34dbb8b045cfabd8fc21fc40599c22a3c6dcbf132faf","7e90084a851f8c43a1c320ed058952e93a6699978dc2e89840310123474d3a2b");
    }
}

