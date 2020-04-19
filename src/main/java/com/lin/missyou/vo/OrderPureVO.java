package com.lin.missyou.vo;

import com.lin.missyou.model.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class OrderPureVO extends Order {
    private Long period;
    private Date createTime;

    public OrderPureVO(Order order,Long period){
        BeanUtils.copyProperties(order,this);
        this.period = period;
    }
}
