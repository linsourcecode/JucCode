package com.example.Unsafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ListUnsafe {
    public static void main(String[] args) {
        List list=new ArrayList();
        for(int i=0;i<1000;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    list.add(UUID.randomUUID().toString().substring(0,5));
                    System.out.println(list);
                   // System.out.println(list.get(j));;
                }
            }).start();
        }
        /*****
         * 读写分离的思想
         * 添加容器时，复制容器，往容器添加数据后，使容器指向新的容器，简单来说，类似快照，在用快照替代了原始的
         * **/
        List<String> alist = new CopyOnWriteArrayList<>();
        for(int i=0;i<=10;i++){

            new Thread(()->{
                alist.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(alist);
            }).start();
    }}
    CopyOnWriteArraySet hashMap=new CopyOnWriteArraySet<String>();
    
}
