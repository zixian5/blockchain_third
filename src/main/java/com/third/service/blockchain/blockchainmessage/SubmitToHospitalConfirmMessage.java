package com.third.service.blockchain.blockchainmessage;

import cn.xjfme.encrypt.test.SecurityTestAll;
import cn.xjfme.encrypt.utils.Util;
import cn.xjfme.encrypt.utils.sm2.SM2SignVO;
import com.google.gson.Gson;
import com.third.service.blockchain.entity.Insurance;
import com.third.service.blockchain.entity.Payment;
import com.third.service.blockchain.entity.Policy;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.xjfme.encrypt.test.SecurityTestAll.genSM2Signature;

public class SubmitToHospitalConfirmMessage {

    private Policy policy;
    private Insurance insurance;
    private Payment payment;
    private String senderPubkey;
    private String senderPrikey;
    private String receiverPubkey;

    public String toJsonString()
    {
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("policy",policy);
        payment.setSelfSign(payment.getDirectPaymentInfoId());
        data.put("paymment",payment);
        data.put("insurance",insurance);

        Map<String,Object> map =new LinkedHashMap<>();
        map.put("sign","no shixian now");
        map.put("senderPubkey",senderPubkey);
        map.put("data",data);

        return new Gson().toJson(map);
    }

    private String sign(String source,String senderPriKey) throws Exception {
        String s5 = Util.byteToHex(source.getBytes());
        SM2SignVO sign = genSM2Signature(senderPriKey, s5);
        String result = sign.getSm2_signForSoft();
        System.out.println("软加密签名结果:" + result);
        return result;
    }

    public String encrypt(String source ,String senderPubkey) throws IOException {
        String SM2Enc = SecurityTestAll.SM2Enc(senderPubkey, source);
        System.out.println("加密:");
        System.out.println("密文:" + SM2Enc);
        return SM2Enc;
    }

    public Policy getPolicy() {
        return policy;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public String getSenderPubkey() {
        return senderPubkey;
    }

    public void setSenderPubkey(String senderPubkey) {
        this.senderPubkey = senderPubkey;
    }

    public String getSenderPrikey() {
        return senderPrikey;
    }

    public void setSenderPrikey(String senderPrikey) {
        this.senderPrikey = senderPrikey;
    }

    public String getReceiverPubkey() {
        return receiverPubkey;
    }

    public void setReceiverPubkey(String receiverPubkey) {
        this.receiverPubkey = receiverPubkey;
    }
}
