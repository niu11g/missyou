package com.lin.missyou.sample.hero;

import com.lin.missyou.sample.ISkill;

public class Camille implements ISkill {
    private String name;
    private String age;

    public Camille(String name,String age){
        this.name = name;
        this.age = age;
    }
    public Camille() {
        System.out.println("Camille a");
    }

    public void g() {
        System.out.println("Camille Q");
    }

    public void w() {
        System.out.println("Camille W");
    }

    public void e() {
        System.out.println("Camille E");
    }

    public void r() {
        System.out.println("Camille R");
    }
}
