package com.example.demo.test;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadTest {

    private static class ThreadPool {
        public ThreadPoolExecutor getThreadPoolExecutor() {
            return new ThreadPoolExecutor(
                    10, // core pool size
                    20, // maximum pool size
                    60L, // keep-alive time for idle threads
                    java.util.concurrent.TimeUnit.SECONDS, // time unit for keep-alive time
                    new java.util.concurrent.LinkedBlockingQueue<>(), // work queue
                    new DefaultThreadFactory("my-thread-pool"), // thread factory
                    new ThreadPoolExecutor.CallerRunsPolicy() // rejection policy
            );
        }
    }

    private final ThreadPool threadPool = new ThreadPool();

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPool.getThreadPoolExecutor();
    }

    public static void main(String[] args) {
        ThreadTest test = new ThreadTest();
        test.getThreadPoolExecutor().execute(() -> {
            System.out.println("Hello from the thread pool!");
            System.out.println("Thread name: " + Thread.currentThread().getName());
        });
    }

}
