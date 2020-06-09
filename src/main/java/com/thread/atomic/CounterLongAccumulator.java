package com.thread.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class CounterLongAccumulator {

    public static void main(String[] args) throws InterruptedException {
        //初始值为0,lambda中y为1，x为上一次求和的保存值
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> {
            return x + y;
        }, 0);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    //区别于LongAdder每次只能+1，这里可以自定义+多少
                    longAccumulator.accumulate(1);
                }
                System.out.println("done...");
            }).start();
        }

        Thread.sleep(6000L);
        System.out.println("执行后longAccumulator的值为：" + longAccumulator.get());
    }

}
