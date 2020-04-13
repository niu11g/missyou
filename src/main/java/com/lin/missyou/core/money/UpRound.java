package com.lin.missyou.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class UpRound implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        //original * discount = 原价 * 折扣价 = 最终价
        BigDecimal actualMoney = original.multiply(discount);
        //把最终价保留两位小数、银行家算法-(HALF-EVEN)。
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.UP);
        return finalMoney;
    }
}
