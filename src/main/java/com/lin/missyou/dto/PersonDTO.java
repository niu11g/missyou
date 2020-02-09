package com.lin.missyou.dto;

import com.lin.missyou.validators.PasswordEqual;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@PasswordEqual(min=1,message = "两次密码不一致")
public class PersonDTO {
    @Length(min = 2,max = 6,message = "XXXX")
    private String name;
    private Integer age;

//    @Valid
//    private SchoolDTO schoolDTO;

    private String password1;
    private String password2;
}
