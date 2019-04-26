package com.third.service.blockchain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Record {
    @Id
    private String medicalRecordInfoId;
    private String treatmentDate;
    private String treatmentHospital;
    private String medicalRecordContent;
    private String pubkey;
    private String doctor;

    public String getMedicalRecordInfoId() {
        return medicalRecordInfoId;
    }

    public void setMedicalRecordInfoId(String medicalRecordInfoId) {
        this.medicalRecordInfoId = medicalRecordInfoId;
    }

    public String getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(String treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentHospital() {
        return treatmentHospital;
    }

    public void setTreatmentHospital(String treatmentHospital) {
        this.treatmentHospital = treatmentHospital;
    }

    public String getMedicalRecordContent() {
        return medicalRecordContent;
    }

    public void setMedicalRecordContent(String medicalRecordContent) {
        this.medicalRecordContent = medicalRecordContent;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
