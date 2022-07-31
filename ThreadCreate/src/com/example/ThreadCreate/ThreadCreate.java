package com.example.ThreadCreate;

import java.util.concurrent.*;

/***
 * 线程创建的三种方法
 * 一 创建一个或者继承Thread类
 * 二 继承Runnable
 * 三 使用Callable结合Future异步任务使用
 *
 * */
public class ThreadCreate {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        }).start();
        new MyThread().run();
        new Thread(new MyThread2()).run();
        ExecutorService executorService2= Executors.newCachedThreadPool();

        Future<String> future = executorService2.submit(new MyThread3());
        future.isDone();
        System.out.println(future.get());
        executorService2.shutdown();


    }
}
class MyThread implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
class MyThread2 extends Thread{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }
}
class MyThread3 implements Callable<String>{

    @Override
    public String call() throws Exception {
        return "Success";
    }
}
