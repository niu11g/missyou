package com.lin.missyou.repository;


import com.lin.missyou.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long>{

    @Query("select c from Coupon c \n" +
            "join c.categoryList ca\n" +
            "join \n" +
            "Activity a on a.id = c.activityId\n" +
            "where ca.id = :cid\n" +
            "and a" +
            ".startTime < :now\n" +
            "and a.endTime > :now")
    List<Coupon> findByCategory(Long cid, Date now);

    @Query("select c from Coupon c \n" +
            "join Activity a on c.activityId = a.id\n" +
            "where c.wholeStore = :isWholeStore\n" +
            "and a.startTime < :now\n" +
            "and a.endTime > :now")
    List<Coupon> findByWholStore(Boolean isWholeStore,Date now);
}
