package com.lin.missyou.repository;

import com.lin.missyou.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    //status = unpaid 延迟消息队列   单个判断则包含了已过期的，未及时更新状态
    //expiredTime > now             单个判断则包含了已取消的
    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now, Integer status, Long uid, Pageable pageable);

    Page<Order> findByUserId(Long uid,Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long uid,Integer status,Pageable pageable);

    Optional<Order> findFirstByUserIdAndId(Long uid,Long oid);

    Optional<Order> findFirstByOrderNo(String orderNo);

    @Modifying
    @Query("update Order o set o.status = :status where o.orderNo = :orderNo")
    int updateStatusByOrderNo(String orderNo,Integer status);

    //未支付才更改为已取消
    @Modifying
    @Query("update Order o set o.status=5 where o.status=1 and o.orderNo=:oid")
    int cancelOrder(Long oid);


}
