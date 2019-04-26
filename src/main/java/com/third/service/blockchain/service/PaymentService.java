package com.third.service.blockchain.service;

import com.bubi.connect.ContractConnect;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.third.service.blockchain.blockchainmessage.SubmitToHospitalConfirmMessage;
import com.third.service.blockchain.entity.Insurance;
import com.third.service.blockchain.entity.Payment;
import com.third.service.blockchain.entity.Policy;
import com.third.service.blockchain.repository.InsuranceRepository;
import com.third.service.blockchain.repository.PaymentRepository;
import com.third.service.blockchain.repository.PolicyRepository;
import com.third.service.blockchain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static cn.xjfme.encrypt.test.SecurityTestAll.SM2Dec;

@Service
public class PaymentService {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Resource
    private Environment environment;

    public void updatePaymentFromBlockchain(String pubkey,String prikey)
    {
        try {
            handleHospitalDecline(pubkey,prikey);
            handleInsuranceConfirm(pubkey,prikey);
            handleInsuranceDecline(pubkey,prikey);
            handleHospitalConfirmFinal(pubkey,prikey);
            handleComplete(pubkey,prikey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitToHospitalConfirm(String paymentId)
    {
        Payment payment = paymentRepository.findByDirectPaymentInfoId(paymentId);
        String insurancePurchasingInfoId = payment.getInsurancePurchasingInfoId();
        String senderPubkey = payment.getPublicKey();
        String senderPrikey = userRepository.findByPubkey(senderPubkey).getPrikey();
        Policy policy = policyRepository.findByInsurancePurchasingInfoId(insurancePurchasingInfoId);
        String insuranceId = policy.getInsuranceId();
        Insurance insurance = insuranceRepository.findByInsuranceId(insuranceId);
        String receiverPubkey = environment.getProperty("hospital.pubkey");
        String contract = environment.getProperty("payment.hospital.confirm.payable");

        SubmitToHospitalConfirmMessage message = new SubmitToHospitalConfirmMessage();
        message.setInsurance(insurance);
        message.setPolicy(policy);
        message.setPayment(payment);
        message.setSenderPrikey(senderPrikey);
        message.setSenderPubkey(senderPubkey);
        String source = message.toJsonString();
        System.out.println(source);

        String encSource = null;
        try {
            encSource = message.encrypt(source,receiverPubkey);
            System.out.println("encSource: "+encSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContractConnect.set(receiverPubkey,encSource,contract);
    }

    //获取医院拒绝
    public void handleHospitalDecline(String pubKey,String priKey) throws IOException {
        String contract = environment.getProperty("payment.hospital.declined");
        String json = ContractConnect.get(pubKey, contract);
        System.out.println(json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = object.get("result").getAsJsonObject();
        if (result.get("value").getAsString().equals("false")) {
            return;
        }

        String[] encValues = result.get("value").getAsString().split(",");

        for (String encValue : encValues) {
            String value = SM2Dec(priKey, encValue);
            System.out.println(value);
            JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
            String paymentId = jsonObject.getAsJsonObject("data").get("paymentId").getAsString();
            System.out.println("paymentId: " + paymentId);
            String publicKey = jsonObject.get("senderPubkey").getAsString();
            System.out.println("senderpubkey:  " + publicKey);
            String sign = jsonObject.get("sign").getAsString();
            System.out.println("sign:  " + sign);

            //应该有个sign验证过程
            Payment payment = paymentRepository.findByDirectPaymentInfoId(paymentId);
            payment.setDirectPaymentStage(-1);
            paymentRepository.saveAndFlush(payment);
        }
    }

    //获取保险公司同意
    public void handleInsuranceConfirm(String pubKey,String priKey) throws IOException {
        String contract = environment.getProperty("payment.insurance.verify");
        String json = ContractConnect.get(pubKey, contract);
        System.out.println(json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = object.get("result").getAsJsonObject();
        if (result.get("value").getAsString().equals("false")) {
            return;
        }

        String[] encValues = result.get("value").getAsString().split(",");

        for (String encValue : encValues) {
            String value = SM2Dec(priKey, encValue);
            System.out.println(value);
            JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
            String paymentId = jsonObject.getAsJsonObject("data").get("paymentId").getAsString();
            System.out.println("paymentId: " + paymentId);
            String publicKey = jsonObject.get("senderPubkey").getAsString();
            System.out.println("senderpubkey:  " + publicKey);
            String sign = jsonObject.get("sign").getAsString();
            System.out.println("sign:  " + sign);

            //应该有个sign验证过程
            Payment payment = paymentRepository.findByDirectPaymentInfoId(paymentId);
            payment.setDirectPaymentStage(2);
            paymentRepository.saveAndFlush(payment);
        }
    }

    //获取医院最终同意
    public void handleHospitalConfirmFinal(String pubKey,String priKey) throws IOException {
        String contract = environment.getProperty("payment.hospital.confirm.payment");
        String json = ContractConnect.get(pubKey, contract);
        System.out.println(json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = object.get("result").getAsJsonObject();
        if (result.get("value").getAsString().equals("false")) {
            return;
        }

        String[] encValues = result.get("value").getAsString().split(",");

        for (String encValue : encValues) {
            String value = SM2Dec(priKey, encValue);
            System.out.println(value);
            JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
            String paymentId = jsonObject.getAsJsonObject("data").get("paymentId").getAsString();
            System.out.println("paymentId: " + paymentId);
            String publicKey = jsonObject.get("senderPubkey").getAsString();
            System.out.println("senderpubkey:  " + publicKey);
            String sign = jsonObject.get("sign").getAsString();
            System.out.println("sign:  " + sign);

            //应该有个sign验证过程
            Payment payment = paymentRepository.findByDirectPaymentInfoId(paymentId);
            payment.setDirectPaymentStage(3);
            paymentRepository.saveAndFlush(payment);
        }
    }

    public void handleInsuranceDecline(String pubKey,String priKey) throws IOException {
        String contract = environment.getProperty("payment.insurance.declined");
        String json = ContractConnect.get(pubKey, contract);
        System.out.println(json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = object.get("result").getAsJsonObject();
        if (result.get("value").getAsString().equals("false")) {
            return;
        }

        String[] encValues = result.get("value").getAsString().split(",");

        for (String encValue : encValues) {
            String value = SM2Dec(priKey, encValue);
            System.out.println(value);
            JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
            String paymentId = jsonObject.getAsJsonObject("data").get("paymentId").getAsString();
            System.out.println("paymentId: " + paymentId);
            String publicKey = jsonObject.get("senderPubkey").getAsString();
            System.out.println("senderpubkey:  " + publicKey);
            String sign = jsonObject.get("sign").getAsString();
            System.out.println("sign:  " + sign);

            //应该有个sign验证过程
            Payment payment = paymentRepository.findByDirectPaymentInfoId(paymentId);
            payment.setDirectPaymentStage(-2);
            paymentRepository.saveAndFlush(payment);
        }
    }

    public void handleComplete(String pubKey,String priKey) throws IOException {
        String contract = environment.getProperty("payment.complete");
        String json = ContractConnect.get(pubKey, contract);
        System.out.println(json);
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        JsonObject result = object.get("result").getAsJsonObject();
        if (result.get("value").getAsString().equals("false")) {
            return;
        }

        String[] encValues = result.get("value").getAsString().split(",");

        for (String encValue : encValues) {
            String value = SM2Dec(priKey, encValue);
            System.out.println(value);
            JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
            String paymentId = jsonObject.getAsJsonObject("data").get("paymentId").getAsString();
            System.out.println("paymentId: " + paymentId);
            String publicKey = jsonObject.get("senderPubkey").getAsString();
            System.out.println("senderpubkey:  " + publicKey);
            String sign = jsonObject.get("sign").getAsString();
            System.out.println("sign:  " + sign);

            //应该有个sign验证过程
            Payment payment = paymentRepository.findByDirectPaymentInfoId(paymentId);
            payment.setDirectPaymentStage(4);
            paymentRepository.saveAndFlush(payment);
        }
    }
}
