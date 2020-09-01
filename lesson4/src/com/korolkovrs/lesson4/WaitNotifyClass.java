package com.korolkovrs.lesson4;

public class WaitNotifyClass {
    private final Object lock = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        WaitNotifyClass w = new WaitNotifyClass();

        Thread t1 = new Thread(() -> {
            w.printA();
        });

        Thread t2 = new Thread(() -> {
            w.printB();
        });

        Thread t3 = new Thread(() -> {
            w.printC();
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public void printA() {
        synchronized (lock) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') {
                        lock.wait();
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'B';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (lock) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        lock.wait();
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'C';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

        public void printC() {
            synchronized (lock) {
                try {
                    for (int i = 0; i < 5; i++) {
                        while (currentLetter != 'C') {
                            lock.wait();
                        }
                        System.out.print(currentLetter);
                        currentLetter = 'A';
                        lock.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
