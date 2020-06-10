package com.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    //entryList（锁池）在ReentrantLock中
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            lock.lock();
            System.out.println("已经抢到锁，准备进入await状态");
            try {
                //线程将进入等待池，相当于synchronized的waitSet
                condition.await();
                System.out.println("被唤醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Thread.sleep(4000L);
        lock.lock();
        condition.signal();
        System.out.println("主线程不释放锁，唤醒了也抢不了锁");
        lock.unlock();
    }

}
