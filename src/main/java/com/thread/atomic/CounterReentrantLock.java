package com.thread.atomic;

import java.util.concurrent.locks.ReentrantLock;

public class CounterReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        ReentrantLock lock = new ReentrantLock();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lock.lock();
                for (int j = 0; j < 10000; j++) {
                    counter.add();
                }
                System.out.println("done...");
                lock.unlock();
            }).start();
        }

        Thread.sleep(6000L);
        System.out.println("执行后count的值为：" + counter.i);
    }

    public static class Counter {
        volatile int i = 0;

        public void add() {
            i++;
        }
    }
}
