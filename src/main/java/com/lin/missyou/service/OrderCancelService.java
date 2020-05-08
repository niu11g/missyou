package com.lin.missyou.service;

import com.lin.missyou.bo.OrderMessageBO;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.ServerErrorException;
import com.lin.missyou.model.Order;
import com.lin.missyou.repository.OrderRepository;
import com.lin.missyou.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderCancelService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Transactional
    public void cancel(OrderMessageBO messageBO){
        if(messageBO.getOrderId()<=0){
            throw new ServerErrorException(9999);
        }
        this.cancel(messageBO.getOrderId());
    }

    private void cancel(Long oid){
        Optional<Order> orderOptional = orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(()->new ServerErrorException(50009));
        int res = orderRepository.cancelOrder(oid);
        if(res!=1){
            return;
        }
        order.getSnapItems().forEach(i->{
            skuRepository.recoverStock(i.getId(),i.getCount().longValue());
        });
    }
}
