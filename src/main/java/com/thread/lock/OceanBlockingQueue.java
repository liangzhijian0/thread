package com.thread.lock;

//利用lock和condition实现一个阻塞队列

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OceanBlockingQueue {
    private int length;
    private static List<Object> list = new ArrayList<>();

    private static Lock lock = new ReentrantLock();
    private static Condition putCondition = lock.newCondition();
    private static Condition takeCondition = lock.newCondition();

    public OceanBlockingQueue(int length) {
        this.length = length;
    }

    public void put(Object object) {
        lock.lock();
        try {
            while (true) {
                if (list.size() < length) {
                    list.add(object);
                    takeCondition.signal();
                    System.out.println("put:" + object);
                    break;
                } else {
                    putCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        lock.lock();
        try {
            while (true) {
                if (list.size() > 0) {
                    Object removeObj = list.remove(0);
                    putCondition.signal();
                    System.out.println("take:" + removeObj);
                    return removeObj;
                } else {
                    takeCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        OceanBlockingQueue oceanBlockingQueue = new OceanBlockingQueue(5);
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                oceanBlockingQueue.put(i);
            }
        }).start();

        Thread.sleep(3000L);
        for (int j = 0; j < 10; j++) {
            oceanBlockingQueue.take();
            Thread.sleep(3000L);
        }
    }
}
