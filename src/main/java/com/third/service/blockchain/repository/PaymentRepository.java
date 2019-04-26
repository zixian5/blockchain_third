package com.third.service.blockchain.repository;

import com.third.service.blockchain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment  ,String> {
    @Query("from Payment p where p.directPaymentInfoId =:directPaymentInfoId")
    Payment findByDirectPaymentInfoId(@Param("directPaymentInfoId") String directPaymentInfoId);
    @Query("from Payment p where p.publicKey=:pubkey")
    List<Payment> findByPublicKey(@Param("pubkey") String pubkey);
}
