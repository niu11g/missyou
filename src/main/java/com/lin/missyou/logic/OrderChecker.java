package com.lin.missyou.logic;

import com.lin.missyou.bo.SkuOrderBO;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.dto.SkuInfoDTO;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.OrderSku;
import com.lin.missyou.model.Sku;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class OrderChecker {

    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;

    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>(  );

    public OrderChecker(OrderDTO orderDTO,List<Sku> serverSkuList,
                        CouponChecker couponChecker,Integer maxSkuLimit){
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public String getLeaderImg(){
        return this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount(){
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public void isOK(){
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(),this.serverSkuList.size());

        for(int i = 0;i < this.serverSkuList.size();i++){
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku,skuInfoDTO);
            this.beyondMaxSkuLimit(skuInfoDTO);
            serverTotalPrice.add(this.caculateSkuOrderPrice(sku,skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku,skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku,skuInfoDTO));
        }
        this.totalPriceIsOk(orderDTO.getTotalPrice(),serverTotalPrice);
        if(this.couponChecker != null){
            this.couponChecker.isOK();
            this.couponChecker.canBeUsed(skuOrderBOList,serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(),serverTotalPrice);
        }
    }

    private void totalPriceIsOk(BigDecimal orderTotalPrice,BigDecimal serverTotalPrice){
        if(orderTotalPrice.compareTo(serverTotalPrice)!=0){
            throw new ParameterException(50005);
        }
    }

    private BigDecimal caculateSkuOrderPrice(Sku sku,SkuInfoDTO skuInfoDTO){
        if(skuInfoDTO.getCount() <= 0){
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    private void skuNotOnSale(int count1,int count2){
        if(count1 != count2){
            throw new ParameterException(50002);
        }
    }

    private void containsSoldOutSku(Sku sku){
        if(sku.getStock() == 0){
            throw new ParameterException(50001);
        }
    }

    private void beyondSkuStock(Sku sku,SkuInfoDTO skuInfoDTO){
        if(sku.getStock() < skuInfoDTO.getCount()){
            throw new ParameterException(50003);
        }
    }

    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO){
        if(skuInfoDTO.getCount() > this.maxSkuLimit){
            throw new ParameterException(50004);
        }
    }

}
