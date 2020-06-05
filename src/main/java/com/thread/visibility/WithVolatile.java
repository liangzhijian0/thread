package com.thread.visibility;

public class WithVolatile {
    public static volatile boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            int i = 0;
            while (isRunning) {
                i++;
            }
            //JVM把volatile变量编译时加上ACC_VOLATILE修饰符，禁止缓存，因此JIT不会把isRunning缓存起来，会重新从堆内存读取。
            System.out.println("thread2执行结束：" + i);
        }).start();

        Thread.sleep(3000L);
        isRunning = false;
        System.out.println("此时isRunning值已经被改变：" + isRunning);
    }
}
