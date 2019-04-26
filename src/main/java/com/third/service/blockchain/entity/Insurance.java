package com.third.service.blockchain.entity;

import cn.xjfme.encrypt.test.SecurityTestAll;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
public class Insurance {
    @Id
    @Column(name = "idinsurance")
    private String insuranceId;
    @Column(name = "insurance_source")
    private String insuranceSource;
    @Column(name = "insurance_duration")
    private String insuranceDuration;
    @Column(name = "insurancePrice")
    private Integer insurancePrice;
    private String  insuranceName;
    private Integer isSpecialMedicalCare;
    private Integer hasSocialSecurity;
    private Integer insuranceAmount;
    private String insurancePeriod;
    private String insuranceDiseaseType;
    private String coveringAge;

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public Integer getIsSpecialMedicalCare() {
        return isSpecialMedicalCare;
    }

    public void setIsSpecialMedicalCare(Integer isSpecialMedicalCare) {
        this.isSpecialMedicalCare = isSpecialMedicalCare;
    }

    public Integer getHasSocialSecurity() {
        return hasSocialSecurity;
    }

    public void setHasSocialSecurity(Integer hasSocialSecurity) {
        this.hasSocialSecurity = hasSocialSecurity;
    }

    public Integer getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Integer insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getInsurancePeriod() {
        return insurancePeriod;
    }

    public void setInsurancePeriod(String insurancePeriod) {
        this.insurancePeriod = insurancePeriod;
    }

    public String getInsuranceDiseaseType() {
        return insuranceDiseaseType;
    }

    public void setInsuranceDiseaseType(String insuranceDiseaseType) {
        this.insuranceDiseaseType = insuranceDiseaseType;
    }

    public String getCoveringAge() {
        return coveringAge;
    }

    public void setCoveringAge(String coveringAge) {
        this.coveringAge = coveringAge;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getInsuranceSource() {
        return insuranceSource;
    }

    public void setInsuranceSource(String insuranceSource) {
        this.insuranceSource = insuranceSource;
    }

    public String getInsuranceDuration() {
        return insuranceDuration;
    }

    public void setInsuranceDuration(String insuranceDuration) {
        this.insuranceDuration = insuranceDuration;
    }

    public Integer getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(Integer insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public String genetateInsuranceId()
    {
        String src = insuranceSource+insuranceDuration+insurancePrice.toString();
        return SecurityTestAll.generateSM3HASH(src).substring(0,32);
    }
}
