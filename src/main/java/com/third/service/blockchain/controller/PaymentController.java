package com.third.service.blockchain.controller;


import cn.xjfme.encrypt.test.SecurityTestAll;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.third.service.blockchain.entity.Insurance;
import com.third.service.blockchain.entity.Payment;
import com.third.service.blockchain.entity.Policy;
import com.third.service.blockchain.entity.User;
import com.third.service.blockchain.repository.PaymentRepository;
import com.third.service.blockchain.repository.PolicyRepository;
import com.third.service.blockchain.repository.UserRepository;
import com.third.service.blockchain.response.GetDirectPaymentInfoIdReponse;
import com.third.service.blockchain.response.GetDirectPaymentInfoListResponse;
import com.third.service.blockchain.response.ResponseCode;
import com.third.service.blockchain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private Environment environment;

    @RequestMapping("/directPaymentProcess/getDirectPaymentInfoList")
    @ResponseBody
    public String GetDirectPaymentInfoList(String email)
    {
        User user = userRepository.findBYEmail(email);
        paymentService.updatePaymentFromBlockchain(user.getPubkey(),user.getPrikey());
        GetDirectPaymentInfoListResponse response =new GetDirectPaymentInfoListResponse();
        response.setCode(ResponseCode.SUCCESS.getCode());
        List<Payment> payments = paymentRepository.findByPublicKey(user.getPubkey());
        response.setPayments(payments);
        return response.toJsonString();
    }

    @RequestMapping("/directPaymentDetail/getDirectPaymentInfo")
    @ResponseBody
    public String GetDirectPaymentInfo(String directPaymentInfoId )
    {
        Payment payment = paymentRepository.findByDirectPaymentInfoId(directPaymentInfoId);
        GetDirectPaymentInfoIdReponse reponse = new GetDirectPaymentInfoIdReponse();
        reponse.setPayment(payment);
        return  reponse.toJSonString();
    }


    @ResponseBody
    @RequestMapping("/insurancePurchasingDetail/submitStartDirectPayment")
    public String submitStartDirectPayment(String insurancePurchasingInfoId)
    {
     //   System.out.println("-----------------"+json+"--------------");
    //    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
    //    String insurancePurchasingInfoId = jsonObject.get("insurancePurchasingInfoId").getAsString();
        //String insurancePurchasingInfoId = json;
        Policy policy = policyRepository.findByInsurancePurchasingInfoId(insurancePurchasingInfoId);
        if(policy == null)
        {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("code",ResponseCode.SUCCESS.getCode());
            return new Gson().toJson(map);
        }
        Payment payment = new Payment();
        payment.setDirectPaymentStage(1);
        payment.setAge(policy.getAge());
        payment.setInsurancePurchasingInfoId(insurancePurchasingInfoId);
        payment.setHealthState(policy.getHealthState());
        payment.setHospital(environment.getProperty("hospital.name"));
        payment.setPublicKey(policy.getPublicKey());
        payment.setIsMale(policy.getIsMale());
        payment.setName(policy.getName());
        payment.setDirectPaymentMoneyAmount(policy.getInsurancePrice());
        payment.setDirectPaymentInfoId(SecurityTestAll.generateSM3HASH(payment.toString()+new Date().getTime()));
        paymentRepository.saveAndFlush(payment);

        paymentService.submitToHospitalConfirm(payment.getDirectPaymentInfoId());

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("code",ResponseCode.SUCCESS.getCode());
        return new Gson().toJson(map);
    }


    @RequestMapping("/paymentTest")
    @ResponseBody
    public String submitPaymentTest(String paymentid)
    {
        paymentService.submitToHospitalConfirm(paymentid);
        return "200";
    }

    @RequestMapping("/hospitalDec")
    @ResponseBody
    public String submitHospitalDec()
    {
        User user = userRepository.findBYEmail("123456");
        try {
            paymentService.handleHospitalDecline(user.getPubkey(),user.getPrikey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "200";
    }

    @RequestMapping("/insConf")
    @ResponseBody
    public String testInsuranceConfirm()
    {
        User user = userRepository.findBYEmail("123456");
        try {
            paymentService.handleInsuranceConfirm(user.getPubkey(),user.getPrikey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "200";
    }

    @RequestMapping("/testFin")
    @ResponseBody
    public String testFinal()
    {
        User user = userRepository.findBYEmail("123456");
        try {
            paymentService.handleHospitalConfirmFinal(user.getPubkey(),user.getPrikey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "200";
    }

    @RequestMapping("/testComp")
    @ResponseBody
    public String testComp()
    {
        User user = userRepository.findBYEmail("123456");
        try {
            paymentService.handleComplete(user.getPubkey(),user.getPrikey());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "200";
    }

}
