package com.lin.missyou.api.V1;
import com.lin.missyou.Service.ActivityService;
import com.lin.missyou.Service.BannerService;
import com.lin.missyou.core.interceptors.ScopeLevel;
import com.lin.missyou.dto.PersonDTO;
import com.lin.missyou.exception.NotFoundException;
import com.lin.missyou.model.Activity;
import com.lin.missyou.model.Banner;
import com.lin.missyou.sample.ISkill;
import com.lin.missyou.vo.ActivityCouponVO;
import com.lin.missyou.vo.ActivityPureVO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getHomeActivity(@PathVariable String name){
        Activity activity = activityService.getByName(name);
        if(activity == null){
            throw new NotFoundException(20005);
        }
        ActivityPureVO vo = new ActivityPureVO(activity);
        return vo;
    }

    @GetMapping("/name/{name}/with_coupon")
    public ActivityCouponVO getActivityWithCoupons(@PathVariable String name){
        Activity activity = activityService.getByName(name);
        if(activity == null){
            throw new NotFoundException(20005);
        }
        ActivityCouponVO vo = new ActivityCouponVO(activity);
        return vo;
    }

}
