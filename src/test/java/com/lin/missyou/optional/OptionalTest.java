package com.lin.missyou.optional;

import org.junit.Test;

import java.util.Optional;

public class OptionalTest {
    @Test
    public void testOptional(){
        Optional<String> empty = Optional.empty();
//        Optional<String> t1 = Optional.of(null);
        Optional<String> t2= Optional.ofNullable("123");
//        String s = t2.get();
        //潜在危险 函数调用栈 变深
//        empty.get();
        //如果t2不为空，则执行后面的输出
        t2.ifPresent(System.out::println);

        String s = t2.orElse("默认值");

        System.out.println(s);
        String s1 = t2.map(t->t+"b").orElse("c");
        t2.map(t->t+"b").ifPresent(System.out::println);

        System.out.println(s1);
        //有返回值是supplier  无返回值是consumer 既可有输入也可有输出是function 既无输入也无输出是runnable
        //consumer supplier runnable function predicate
    }

}
