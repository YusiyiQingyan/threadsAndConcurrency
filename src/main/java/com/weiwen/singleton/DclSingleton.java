package com.weiwen.singleton;

/**
 * @author weiwen
 * @email yusiyiqingyan@163.com
 * Created by weiwen on 2020/7/3
 */

/**
 * 线程安全的单例设计模式（双端检锁式 double check）
 */
public class DclSingleton {

    /**
     * 必须加上volatile关键字 禁止底层指令重排
     * 因为instance = new DclSingleton(); 对应的字节码操作不是原子的 可能发生指令重排
     */
    private static volatile DclSingleton instance = null;

    /**
     * 要实现单例 构造器必须私有化
     */
    private DclSingleton() {
        System.out.println(Thread.currentThread().getName() + "\t 调用构造器");
    }

    /**
     * 双重检测方式 即在加锁前后分别进行检测
     * 第一重检测对于唯一实例来说并不是一定要加 主要是为了提高程序性能 多线程环境下，只要其他线程发现instance不为空，就不用进入同步代码块
     * 第二重检测必须要加 只有这样才能保证单例
     *
     * @return
     */
    public static DclSingleton getInstance() {
        if (instance == null) {
            synchronized (DclSingleton.class) {
                if (instance == null) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                DclSingleton.getInstance();
            }, "Thread" + String.valueOf(i)).start();
        }

    }
}