package com.thread.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
    private static AtomicReference<Thread> atomicReference = new AtomicReference();

    public void lock(){
        while (!atomicReference.compareAndSet(null,Thread.currentThread())) {
            //do nothing
        }
    }

    public void unLock(){
        while(!atomicReference.compareAndSet(Thread.currentThread(),null)){
            //do nothing
        };
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        spinLock.lock();
        // do something,这里可以做一连串的业务
        spinLock.unLock();
    }
}
