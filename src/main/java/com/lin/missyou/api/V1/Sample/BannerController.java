package com.lin.missyou.api.V1.Sample;
import com.lin.missyou.Service.BannerService;
import com.lin.missyou.dto.PersonDTO;
import com.lin.missyou.sample.ISkill;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {
    // 编译项目
    // 重启服务器
//    @Autowired
//    @Autowired  --构造注入
//    public BannerController(Diana diana){
//        this.diana = diana;
//    }

    @Autowired
//  @Qualifier("camille")
    private ISkill irelia;

//    @Autowired
//    private IConnection iConnection;

    @Autowired
    private BannerService bannerService;

//    @GetMapping("/test/{id2}")
    @PostMapping("/test/{id}")
    public PersonDTO test(//@PathVariable @Max(value = 10,message = "id不可以大于10") Integer id,
                       @PathVariable @Range(min = 1,max = 10,message = "id应该在1到10之间") Integer id,
                       @RequestParam @Length(min=4) String name,
                       @RequestBody @Valid PersonDTO personDTO) {
        irelia.r();
        PersonDTO dto = PersonDTO.builder()
                .name("7yue")
                .age(18)
                .build();
//        throw new ForbiddenException(10001);
//        throw new Exception("这里错了");
        return dto;
    }

//    @GetMapping("/test1")
//    public void test1(){
//        iConnection.getConnection();
//    }
}
