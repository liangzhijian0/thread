package com.thread.atomic;

public class CounterIssue {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    counter.add();
                }
                System.out.println("done...");
            }).start();
        }

        Thread.sleep(6000L);
        System.out.println("执行后count的值为：" + counter.i);
    }

    public static class Counter {
        volatile int i = 0 ;

        public void add () {
            i++;
        }
    }


}
