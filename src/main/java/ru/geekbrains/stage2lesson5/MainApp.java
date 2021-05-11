package ru.geekbrains.stage2lesson5;

import java.util.Arrays;

public class MainApp {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        method1();
        try {
            method2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void method1() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = resolveElementOfArray(i, arr[i]);
        }
        System.out.println("Время вычисления в одном потоке:");
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void method2() throws InterruptedException {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = resolveElementOfArray(i, a1[i]);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = resolveElementOfArray(i + HALF, a2[i]);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Время вычисления в двух потоках:");
        System.out.println(System.currentTimeMillis() - a);
    }

    public static float resolveElementOfArray(int i, float elementOfArray) {
        return ((float)(elementOfArray * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2)));
    }

}
