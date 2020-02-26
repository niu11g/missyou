package com.lin.missyou.api.V1;

import com.lin.missyou.Service.BannerService;
import com.lin.missyou.Service.SpuService;
import com.lin.missyou.exception.NotFoundException;
import com.lin.missyou.model.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {
    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive Long id){
        Spu spu = this.spuService.getSpuById(id);
        if(spu==null){
            throw new NotFoundException(20000);
        }
        return spu;
    }

    @GetMapping("/latest")
    public List<Spu> getLatestSpuList(){
        return this.spuService.getLatestPagingSpu();
    }
}
