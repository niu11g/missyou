package com.lin.missyou.service;

import com.lin.missyou.bo.OrderMessageBO;
import com.lin.missyou.core.enumeration.OrderStatus;
import com.lin.missyou.exception.http.ServerErrorException;
import com.lin.missyou.model.Order;
import com.lin.missyou.repository.OrderRepository;
import com.lin.missyou.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CouponBackService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional
    public void returnBack(OrderMessageBO bo){
        Long couponId = bo.getCouponId();
        Long uid = bo.getUserId();
        Long orderId = bo.getOrderId();

        if(couponId == -1){
            return;
        }
        Optional<Order> optional = orderRepository.findFirstByUserIdAndId(uid,orderId);
        Order order = optional.orElseThrow(()->{
            throw new ServerErrorException(9999);
        });

        if(order.getStatusEnum().equals(OrderStatus.UNPAID)
        || order.getStatusEnum().equals(OrderStatus.CANCELED)){
            return;
        }

    }
}
