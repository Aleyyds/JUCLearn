package ricardo.cn.TheActivityOfTheLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 * Java 死锁产生的四个必要条件：
 *
 * 1. 互斥条件，即当资源被一个线程使用（占有）时，别的线程不能使用
 * 2. 不可剥夺条件，资源请求者不能强制从资源占有者手中夺取资源，资源只能由资源占有者主动释放
 * 3. 请求和保持条件，即当资源请求者在请求其他的资源的同时保持对原有资源的占有
 * 4. 循环等待条件，即存在一个等待循环队列：p1 要 p2 的资源，p2 要 p1 的资源，形成了一个等待环路
 */
@Slf4j
public class DeadLock {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(() -> {

            synchronized (a) {
                log.debug("lock A");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (b) {
                        log.debug("lock b");
                        log.debug("do something..");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });


        Thread t2 = new Thread(() -> {

            synchronized (b) {
                log.debug("lock B");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    synchronized (a) {
                        log.debug("lock A");
                        log.debug("do something...");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        });

        t1.start();
        t2.start();
    }
}
