package com.lin.missyou.api.V1;
import com.lin.missyou.Service.BannerService;
import com.lin.missyou.sample.IConnection;
import com.lin.missyou.sample.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/banner")
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
    private ISkill iSkill;

//    @Autowired
//    private IConnection iConnection;

    @Autowired
    private BannerService bannerService;

    @GetMapping("/test")
    public String test() throws Exception {
        iSkill.r();
        throw new Exception("这里错了");
//        return "Hello,九十月";
    }

//    @GetMapping("/test1")
//    public void test1(){
//        iConnection.getConnection();
//    }
}
