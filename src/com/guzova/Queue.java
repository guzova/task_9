package com.guzova;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class Queue {
    java.util.Queue<String> tasks;

    public Queue() {
        this.tasks = new LinkedList<>();
    }

    public void work() {
        Printer printers[] = {new Printer("firstPrinter"), new Printer("secondPrinter")};
        for (Printer p : printers)
            p.start();
        long lastSymbol = 0;
        while (true) {
            try {
                RandomAccessFile file = new RandomAccessFile("input.txt", "r");
                file.seek(lastSymbol);
                String line;
                while ((line = file.readLine()) != null) {
                    if (!line.equals("")) {
                        tasks.add(line);
                        System.out.println("+ " + line);
                    }
                }
                lastSymbol = file.length();
                file.close();
            } catch (IOException e) {
            }
            for (int i = 0; i < printers.length && !tasks.isEmpty(); i++) {
                if (!printers[i].isBusy()) {
                    String task = tasks.peek();
                    if (printers[i].setTask(task)) {
                        System.out.println("task " + task + " is set.");
                        tasks.remove();
                    }
                }
            }
        }
    }
}
