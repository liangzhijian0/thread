package com.thread.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class OceanReentrantLock implements Lock {

    //重入次数
    private static AtomicInteger count = new AtomicInteger(0);
    //锁的拥有者
    private static AtomicReference<Thread> owner = new AtomicReference<>();
    //锁池
    private static LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public boolean tryLock() {
        int ct = count.get();
        //锁未被占用
        if (ct == 0) {
            //抢锁成功
            if (count.compareAndSet(ct, ct + 1)) {
                owner.set(Thread.currentThread());
                return true;
            }
            return false;
        } else {
            //如果是当前线程，继续重入
            if (Thread.currentThread() == owner.get()) {
                count.set(ct + 1);
                return true;
            }
            return false;
        }
    }

    @Override
    public void lock() {
        //只要tryLock失败，就会进入锁池
        if (!tryLock()) {
            //确保写进锁池一次就好了
            waiters.offer(Thread.currentThread());
            //使用循环应对为唤醒
            for (; ; ) {
                //当前线程变成头部时，应该轮到它去抢锁（相对公平）
                if (Thread.currentThread() == waiters.peek()) {
                    if (tryLock()) {
                        waiters.poll();
                        return;
                    } else {
                        LockSupport.park();
                    }
                } else {
                    //应对为唤醒时，继续等待
                    LockSupport.park();
                }
            }
        }
    }

    public boolean tryUnlock() {
        int ct = count.get();
        if (Thread.currentThread() != waiters.peek()) {
            throw new IllegalMonitorStateException();
        }
        count.set(ct - 1);
        if (ct - 1 == 0) {
            owner.compareAndSet(Thread.currentThread(), null);
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        if (tryLock() && waiters.peek() != null) {
            LockSupport.unpark(waiters.peek());
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
