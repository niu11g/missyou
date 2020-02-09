package com.lin.missyou.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SchoolDTO {

    @Length(min = 2,max = 6,message = "XXXX")
    private String schoolName;
}
