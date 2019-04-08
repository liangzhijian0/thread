package com.thread;

/**
 * @author Ocean Liang
 * @date 4/8/2019
 */
public class StartWithRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("this is implements Runnable");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new StartWithRunnable());
        thread.start();
    }
}
