package com.example.objectlock;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *使用多线程的情况下，使用该demo会发生多次购入失败的情况
 * 要求：
 *   失败十次停止工作
 * 解决方法一
 * 加 synchronized =》阻塞线程
 *     优化点：可以减少synchronized的同步的代码块
 * 使用lock锁
 *
 *
 * */
public class demo1 {
    public static void main(String[] args) {
        book3 book=new book3();
        Long start = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            new Thread(()->{
                for(int j=0;j<10;j++){
                    book.sale(10);

                    // System.out.println(list.get(j));;
                }
            }).start();

        }
        Long end = System.currentTimeMillis();


        System.out.println((end-start)+","+book.flag);
    }
}
class book{
    List<Integer> integers=new ArrayList<>();
    int flag=0;
    int book_num=100;
    int sale(int nums){
        try {
            Thread.sleep(1);
            if((book_num-nums)>0){
                System.out.println("购入，库存"+book_num);
                book_num=book_num-nums;
                Thread.sleep(1);
                integers.add(book_num);

                return 1;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("购入失败");
        flag=flag+1;

        //购入失败加库存
        produce();
        return 0;
    }
    void produce(){
        try {
            Thread.sleep(1);
            book_num = book_num+100;
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }




    }
}

class book1{

    int flag=0;
    int book_num=100;
    int sale(int nums){
        try {
            Thread.sleep(1);
            synchronized (this){
                if((book_num-nums)>0){
                    //System.out.println("购入，库存"+book_num);
                    book_num=book_num-nums;
                    Thread.sleep(1);


                    return 1;
                }
                else{
                    //System.out.println("购入失败");
                    //购入失败加库存
                    flag=flag+1;
                    produce();
                }

            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return 0;
    }
      void produce(){
        try {
            Thread.sleep(1);
            book_num = book_num+100;
            Thread.sleep(1);
           // System.out.println("补充100本书");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }




    }
}
class book3{

    int flag=0;
    Lock lock= new ReentrantLock();
    int book_num=100;
    int sale(int nums){
        try {
            lock.lock();
            Thread.sleep(1);

                if((book_num-nums)>0){
                    //System.out.println("购入，库存"+book_num);
                    book_num=book_num-nums;
                    Thread.sleep(1);


                    return 1;
                }
                else{
                    //System.out.println("购入失败");
                    //购入失败加库存
                    flag=flag+1;
                    produce();
                }




        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }


        return 0;
    }
    void produce(){
        try {
            Thread.sleep(1);
            book_num = book_num+100;
            Thread.sleep(1);
            // System.out.println("补充100本书");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }




    }
}
