package com.thread.visibility;

public class WithoutVolatile {
    public static boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            int i = 0;
            while (isRunning) {
                i++;
            }
            //永远不会执行，isRunning被JIT缓存，即使主线程修改为false也不可见
            System.out.println("thread2执行结束：" + i);
        }).start();

        Thread.sleep(3000L);
        isRunning = false;
        System.out.println("此时isRunning值已经被改变：" + isRunning);
    }
}
