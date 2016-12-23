package com.guzova;

public class Printer extends Thread {
    private String name;
    private String task = null;
    private long lastStart = 0;

    public Printer(String name) {
        this.name = name;
    }

    public synchronized boolean isBusy() {
        return task != null;
    }

    public synchronized boolean setTask(String task) {
        if (!isBusy()) {
            this.task = task;
            return true;
        }
        return false;
    }

    public long timeStarted() {
        return lastStart;
    }

    @Override
    public void run() {
        while (true) {
            if (task != null) {
                System.out.println(name);
                synchronized (task) {
                    lastStart = System.currentTimeMillis();
                    try {
                        long time = (long) (Math.random() * 6000);
                        System.out.println(name + "\t\t" + time);
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(task + ". Время ожидания печати больше 5 секунд!");
                    } finally {
                        System.out.println(name + "\t\t" + task);
                    }
                    task = null;
                    System.out.println(name + "\t\tdone!");
                }
            }
        }
    }
}
