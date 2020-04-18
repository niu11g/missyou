package com.lin.missyou.api.V1;

import com.lin.missyou.Service.OrderService;
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
//        this.orderService.placeOrder(uid,orderDTO);
        return null;
    }

}
