package com.third.service.blockchain.repository;

import com.third.service.blockchain.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record,String> {
    @Query("from Record r where r.medicalRecordInfoId=:mrid")
    Record findBymMedicalRecordInfoId(@Param("mrid") String medicalRecordInfoId);

    @Query("from Record  r where r.pubkey=:pb")
    List<Record> findByPubkey(@Param("pb") String pubkey);
}
