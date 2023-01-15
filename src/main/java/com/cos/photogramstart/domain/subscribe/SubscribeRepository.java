package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Modifying
    @Query(value = "insert into subscribe(fromUserId, toUserId, create) values(:fromUserId, :toUserId, now())", nativeQuery = true)
    void nSubscribe(Long fromUserId, Long toUserId);

    @Modifying
    @Query(value = "delete from subscribe where fromUserId = :fromUserId and toUserId = :toUserId", nativeQuery = true)
    void nUnSubscribe(Long fromUserId, Long toUserId);
}
