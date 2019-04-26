package com.third.service.blockchain.entity;

import cn.xjfme.encrypt.test.SecurityTestAll;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity
public class Payment {
    @Id
    @Column(name = "payment_id")
    private  String directPaymentInfoId;
    private  String name;
    private  Integer age;
    private  int isMale;
    private  String healthState;
    private  String publicKey;
    private  int directPaymentMoneyAmount;
    private  String diagnosticResult;
    private  String hospital;
    private  String medicalDescription;
    private  String insurancePurchasingInfoId;
    private int directPaymentStage;
    @Transient
    private String selfSign;

    public String getSelfSign() {
        return selfSign;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setSelfSign(String selfSign) {
        this.selfSign = selfSign;
    }

    public String generateId(){
        return SecurityTestAll.generateSM3HASH(toString());
    }
    public String getDirectPaymentInfoId() {
        return directPaymentInfoId;
    }

    public void setDirectPaymentInfoId(String directPaymentInfoId) {
        this.directPaymentInfoId = directPaymentInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getIsMale() {
        return isMale;
    }

    public void setIsMale(int isMale) {
        this.isMale = isMale;
    }

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getDirectPaymentMoneyAmount() {
        return directPaymentMoneyAmount;
    }

    public void setDirectPaymentMoneyAmount(int directPaymentMoneyAmount) {
        this.directPaymentMoneyAmount = directPaymentMoneyAmount;
    }

    public String getDiagnosticResult() {
        return diagnosticResult;
    }

    public void setDiagnosticResult(String diagnosticResult) {
        this.diagnosticResult = diagnosticResult;
    }

    public String getMedicalDescription() {
        return medicalDescription;
    }

    public void setMedicalDescription(String medicalDescription) {
        this.medicalDescription = medicalDescription;
    }

    public String getInsurancePurchasingInfoId() {
        return insurancePurchasingInfoId;
    }

    public void setInsurancePurchasingInfoId(String insurancePurchasingInfoId) {
        this.insurancePurchasingInfoId = insurancePurchasingInfoId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "directPaymentInfoId='" + directPaymentInfoId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isMale=" + isMale +
                ", healthState='" + healthState + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", directPaymentMoneyAmount=" + directPaymentMoneyAmount +
                ", diagnosticResult='" + diagnosticResult + '\'' +
                ", hospital='" + hospital + '\'' +
                ", medicalDescription='" + medicalDescription + '\'' +
                ", insurancePurchasingInfoId='" + insurancePurchasingInfoId + '\'' +
                ", directPaymentStage=" + directPaymentStage +
                ", selfSign='" + selfSign + '\'' +
                '}';
    }

    public int getDirectPaymentStage() {
        return directPaymentStage;
    }

    public void setDirectPaymentStage(int directPaymentStage) {
        this.directPaymentStage = directPaymentStage;
    }
}

enum PaymentStage{
    ALL_STAGES(-2),APPLICATION(0),HOSPITAL_CONFIRM_PAYABLE(1),INSURANCE_COMPANY_VERIFY_AND_PAY(2),HOSPITAL_CONFIRM_PAYMENT(3),COMPLETE(4),HOSPITAL_CONFIRM_PAYABLE_DECLINED(-1),INSURANCE_COMPANY_VERIFY_AND_PAY_DECLINED(-2);
    private int stage;

    PaymentStage(int stage) {
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
