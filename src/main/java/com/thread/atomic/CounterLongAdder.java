package com.thread.atomic;

import java.util.concurrent.atomic.LongAdder;

public class CounterLongAdder {

    public static void main(String[] args) throws InterruptedException {
        //初始值为0
        LongAdder longAdder = new LongAdder();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    //每次只能+1
                    longAdder.increment();
                }
                System.out.println("done...");
            }).start();
        }

        Thread.sleep(6000L);
        System.out.println("执行后longAdder的值为：" + longAdder.sum());
    }

}
