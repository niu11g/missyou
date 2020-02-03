package com.lin.missyou.sample.hero;

import com.lin.missyou.sample.ISkill;
import org.springframework.stereotype.Component;

@Component
public class Diana implements ISkill {

    public Diana() {
        System.out.println("Diana a");
    }

    public void g() {
        System.out.println("Diana Q");
    }

    public void w() {
        System.out.println("Diana W");
    }

    public void e() {
        System.out.println("Diana E");
    }

    public void r() {
        System.out.println("Diana R");
    }
}
