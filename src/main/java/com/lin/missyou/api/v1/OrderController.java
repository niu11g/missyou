package com.lin.missyou.api.v1;

import com.lin.missyou.service.OrderService;
import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.interceptors.ScopeLevel;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.logic.OrderChecker;
import com.lin.missyou.vo.OrderIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid = LocalUser.getUser().getId();
        //先较验
        OrderChecker orderChecker = this.orderService.isOK(uid,orderDTO);
        Long oid = this.orderService.placeOrder(uid,orderDTO,orderChecker);

        return new OrderIdVO(oid);
    }

}
