package com.third.service.blockchain.entity;

import cn.xjfme.encrypt.test.SecurityTestAll;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Policy {
    @Id
    @Column(name = "idpolicy")
   // @GeneratedValue(strategy = GenerationType.AUTO)
    private String insurancePurchasingInfoId;
    private String name;
    private int isMale;
    private int age;
    private String healthState;
    private String publicKey;
    private String insuranceCompany;
    private String insuranceType;
    private String insurancePurchasingTime;
    private String insurancePeriod;
    private  int insurancePurchasingStage;
    private int insurancePrice;
    private String responsiblePersonId;
    private String responsiblePersonName;
    private String insuranceId;
    private String insuranceSign;


    public String getInsuranceSign() {
        return insuranceSign;
    }

    public void setInsuranceSign(String insuranceSign) {
        this.insuranceSign = insuranceSign;
    }

    public int getIsMale() {
        return isMale;
    }

    public void setIsMale(int isMale) {
        this.isMale = isMale;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String generateInsurancePurchasingInfoId()
    {
        return SecurityTestAll.generateSM3HASH(toString()).substring(0,32);
    }

    public int getInsurancePurchasingStage() {
        return insurancePurchasingStage;
    }

    public void setInsurancePurchasingStage(int insurancePurchasingStage) {
        this.insurancePurchasingStage = insurancePurchasingStage;
    }

    public String getInsurancePurchasingInfoId() {
        return insurancePurchasingInfoId;
    }

    public void setInsurancePurchasingInfoId(String insurancePurchasingInfoId) {
        this.insurancePurchasingInfoId = insurancePurchasingInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMale() {
        return isMale;
    }

    public void setMale(int male) {
        isMale = male;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsurancePurchasingTime() {
        return insurancePurchasingTime;
    }

    public void setInsurancePurchasingTime(String insurancePurchasingTime) {
        this.insurancePurchasingTime = insurancePurchasingTime;
    }

    public String getInsurancePeriod() {
        return insurancePeriod;
    }

    public void setInsurancePeriod(String insurancePeriod) {
        this.insurancePeriod = insurancePeriod;
    }

    public int getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(int insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public String getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(String responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }

    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }
}

enum PurshasingStage{
    All_STAGES(-2),APPLICATION(0),INSURANCE_COMPANY_VERIFY(1),PAY(2),COMPLETE(3),INSURANCE_COMPANY_VERIFY_DECLINED(-1);
    private int code;

    PurshasingStage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

