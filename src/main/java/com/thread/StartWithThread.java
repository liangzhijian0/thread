package com.thread;

/**
 * @author Ocean Liang
 * @date 4/8/2019
 */
public class StartWithThread extends Thread{
    @Override
    public void run(){
        System.out.println("this is extends Thread");
    }

    public static void main(String[] args) {
        StartWithThread startWithThread = new StartWithThread();
        startWithThread.start();
    }

}
