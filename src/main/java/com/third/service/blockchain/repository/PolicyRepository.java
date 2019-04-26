package com.third.service.blockchain.repository;

import com.third.service.blockchain.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,String> {
    @Query("from Policy p where p.insurancePurchasingInfoId =:infoId")
    Policy findByInsurancePurchasingInfoId(@Param("infoId") String infoId);

    @Query("from Policy p where p.publicKey=:pk")
    List<Policy> findAllByPublicKey(@Param("pk") String pubKey);
}
