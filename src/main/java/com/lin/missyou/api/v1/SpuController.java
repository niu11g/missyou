package com.lin.missyou.api.v1;

import com.lin.missyou.service.SpuService;
import com.lin.missyou.bo.PageCounter;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.model.Spu;
import com.lin.missyou.util.CommonUtil;
import com.lin.missyou.vo.PagingDozer;
import com.lin.missyou.vo.SpuSimpleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/spu")
//@Validated
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
    @GetMapping("/id/{id}/sim")
    public SpuSimpleVo getDetailSpuS(@PathVariable @Positive(message = "{id.positive}") Long id){
        Spu spu = this.spuService.getSpuById(id);
        SpuSimpleVo vo = new SpuSimpleVo();
        BeanUtils.copyProperties(spu,vo);
        return vo;
    }
    @GetMapping("/latest")
    //pageNum、Size
    public PagingDozer<Spu,SpuSimpleVo> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                              @RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.converToPageParameter(start,count);
        Page<Spu> page = this.spuService.getLatestPagingSpu(pageCounter.getPage(),pageCounter.getCount());
        return new PagingDozer<>(page,SpuSimpleVo.class);
//        Paging<Spu> paging = new Paging<>(spuList);
//        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
//        List<SpuSimpleVo> vos = new ArrayList<>();
//        paging.getItems().forEach(s->{
//            SpuSimpleVo vo = mapper.map(s, SpuSimpleVo.class);
//            vos.add(vo);
//        });
//        return vos;
    }
    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu,SpuSimpleVo> getByCategoryId(@PathVariable @Positive Long id,
                                                        @RequestParam(name = "is_root",defaultValue = "false") Boolean isRoot,
                                                        @RequestParam(name = "start",defaultValue = "0") Integer start,
                                                        @RequestParam(name = "count",defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.converToPageParameter(start,count);
        Page<Spu> page = this.spuService.getByCategory(id,isRoot,start,count);
        return new PagingDozer<>(page,SpuSimpleVo.class);
    }
}
