package com.thread.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldupdaterDemo {
    private static AtomicIntegerFieldUpdater<User> atom = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        User user = new User(100);
        atom.addAndGet(user, 50);
        System.out.println("the value of age after invoke addAndGet method is: " + user.getAge());
    }

    static class User {
        volatile int age;

        public User(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }


}

