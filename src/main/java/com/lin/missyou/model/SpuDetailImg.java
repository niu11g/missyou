package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Setter
@Getter
public class SpuDetailImg extends BaseEntity {
    @Id
    private Long id;
    private String img;
    private Long spuId;
    private Long index;
}
