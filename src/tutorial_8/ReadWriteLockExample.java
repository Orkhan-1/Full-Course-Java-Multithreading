package src.tutorial_8;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int data = 0;

    public void write(int value) {
        rwLock.writeLock().lock();
        try {
            data = value;
            System.out.println(Thread.currentThread().getName() + " wrote " + value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " read " + data);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample ex = new ReadWriteLockExample();

        Runnable writer = () -> ex.write((int) (Math.random() * 100));
        Runnable reader = () -> ex.read();

        new Thread(writer, "Writer").start();
        new Thread(reader, "Reader1").start();
        new Thread(reader, "Reader2").start();
    }
}
