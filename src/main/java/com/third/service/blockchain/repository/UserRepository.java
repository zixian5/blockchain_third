package com.third.service.blockchain.repository;

import com.third.service.blockchain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("from User u where  u.username =:username")
    User findByUsername(@Param("username") String username);

    @Query("from User u where u.email =:email")
    User findBYEmail(@Param("email") String email);

    @Query("from User u where u.pubkey=:puk")
    User findByPubkey(@Param("puk") String pubkey);
}

