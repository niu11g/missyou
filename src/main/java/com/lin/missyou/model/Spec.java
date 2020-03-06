package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Setter
@Getter
public class Spec{
    @Id
    private Long keyId;
    private String key;
    private Long valueId;
    private String value;
}
