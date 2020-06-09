package com.thread.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class CounterUnsafe {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    counter.add();
                }
                System.out.println("done...");
            }).start();
        }

        Thread.sleep(6000L);
        System.out.println("执行后count的值为：" + counter.i);
    }

    public static class Counter {
        volatile int i = 0;
        private static Unsafe unsafe = null;
        private static long valueOffset;

        static {
            try {
                Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeField.setAccessible(true);
                unsafe = (Unsafe) theUnsafeField.get(null);

                //获取i值在内存条中的偏移量
                Field ifield = Counter.class.getDeclaredField("i");
                valueOffset = unsafe.objectFieldOffset(ifield);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public void add() {
            //CAS自旋
            for (;;){
                int currentValue = unsafe.getIntVolatile(this, valueOffset);
                if (unsafe.compareAndSwapInt(this, valueOffset, currentValue, currentValue + 1)){
                    break;
                }
            }
        }
    }
}
