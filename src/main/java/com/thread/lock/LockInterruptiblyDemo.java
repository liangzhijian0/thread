package com.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyDemo {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        Thread thread = new Thread(() -> {
            System.out.println("开始加锁等待");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("被通知而中断了，不再等待");
        });
        thread.start();
        Thread.sleep(4000L);
        thread.interrupt();
    }
}
