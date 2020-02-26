package com.lin.missyou.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Setter
@Getter
public class SpuImg extends Serializers.Base {
    @Id
    private Long id;
    private String img;
    private Long spuId;
}
