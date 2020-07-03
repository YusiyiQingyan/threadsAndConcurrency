package com.weiwen.volatiledetail;

import java.util.concurrent.TimeUnit;

/**
 * @author weiwen
 * Created by weiwen on 2020/7/2
 */

class MyData {
    //volatile就是保证了主线程和线程的可见性
    volatile int number = 0;

    public void addTO60() {
        this.number = 60;
    }

    //请注意，此时number前面是加了volatile关键字修饰的，volatile不保证原子性
    public void addPlusPlus() {
        number++;
    }
}

/**
 * 1.验证volatile的可见性
 * 1.1 假如int number = 0; number变量之前没有添加volatile关键字修饰，没有可见性
 * 1.2 添加了volatile，可以解决可见性问题
 * <p>
 * 2.验证volatile不保证原子性
 * 2.1 原子性是什么意思？  其实就是看最终一致性能不能保证
 * 不可分割，完整性，
 * 也即某个线程正在做某个业务业务的时候，中间不可以被加塞或者被分割，需要整体完整。
 * 要么同时成功，要么同时失败。
 * <p>
 * 2.2 volatile不保证原子性的案例演示
 * <p>
 * 2.3 volatile为什么不能保证原子性?
 */
public class VolatileDemo {
    public static void main(String[] args) {


        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        //需要等待上面20个线程都全部计算完成后，再用main线程取得最终的结果值，看是多少
        while (Thread.activeCount() > 2) {  //说明线程还没有算完
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t finally number value: " + myData.number);
    }

    //可以保证可见性，及时通知其他线程
    private static void seeOkByVolatile() {
        MyData myData = new MyData();//线程操作资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            //线程暂停3秒钟
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //3秒钟以后将把0改为60
            myData.addTO60();
            System.out.println(Thread.currentThread().getName() + "\t updated number value:" + myData.number);
        }, "AAA").start();

        //第二个线程就是我们的main线程
        while (myData.number == 0) {
            //main主线程就一直在这里等待循环，直到number不再等于零
        }
        //若这句话打印出来了，说明main主线程感知到了number的值发生了变化，那么此时可见性就会被触发
        System.out.println(Thread.currentThread().getName() + "\t mission is over,main get number value:" + myData.number);  //这个是main线程
    }
}


