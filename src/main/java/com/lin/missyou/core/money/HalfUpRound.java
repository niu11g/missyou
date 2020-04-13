package com.lin.missyou.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

//@Component
public class HalfUpRound implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        //original * discount = 原价 * 折扣价 = 最终价
        BigDecimal actualMoney = original.multiply(discount);
        //把最终价保留两位小数、四舍五入-(HALF-UP)。
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_UP);
        return finalMoney;
    }
}
