package com.lin.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lin.missyou.dto.OrderAddressDTO;
import com.lin.missyou.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`order`")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private Date expiredTime;
    private Date placedTime;
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public void setSnapItems(List<OrderSku> orderSkuList){
        if(orderSkuList.isEmpty()){
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems(){
        List<OrderSku> list = GenericAndJson.jsonToObject(this.snapItems, new TypeReference<List<OrderSku>>() {
        });
        return list;
    }

    public OrderAddressDTO getSnapAddress(){
        if(this.snapAddress == null){
            return null;
        }
        OrderAddressDTO o = GenericAndJson.jsonToObject(this.snapAddress, new TypeReference<OrderAddressDTO>() {
        });
        return o;
    }

    public void setSnapAddress(OrderAddressDTO address){
        this.snapAddress = GenericAndJson.objectToJson(address);
    }
}
