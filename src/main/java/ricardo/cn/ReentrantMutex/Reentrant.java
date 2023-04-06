package ricardo.cn.ReentrantMutex;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 特点：
 * 1、可中断
 * 2、可重入
 * 3、可设置超时时间
 * 4、可以设置为公平锁
 * 5、支持条件变量
 * <p>
 * 可重入最大用处：解决死锁
 */

@Slf4j
public class Reentrant {

    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();

        try {
            log.debug("enter main");
            m1();
        } finally {
            lock.unlock();
        }
    }

    public static void m1(){
        lock.lock();

        try {
            log.debug("enter m1");
            m2();
        } finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();

        try {
            log.debug("enter m2");

        } finally {
            lock.unlock();
        }
    }

}
