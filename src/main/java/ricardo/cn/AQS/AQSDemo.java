package ricardo.cn.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * AQS 全称是AbstractQueuedSynchronizer,是阻塞式锁和相关的同步器工具的框架
 *
 * 公平锁与非公平锁主要体现在: 加锁的时候
 *      1、非公平锁在尝试加锁的时候，是直接去竞争锁的，没有检查阻塞队列
 *      2、公平锁在尝试加锁的时候，先检查阻塞队列中是否有其他的线程，如果没有则会去竞争这把锁，如果有则加入阻塞队列
 *
 */
@Slf4j
public class AQSDemo {
    public static void main(String[] args) {

        SLock lock = new SLock();


        new Thread(()->{
            lock.lock();
            log.debug("locking...");
            lock.lock();
            try {
                log.debug("locking...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                log.debug("locking...");
            }finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        },"t2").start();
    }
}

//实现自定义的不可重入锁
class SLock implements Lock {

    //独占锁
    static class MSync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int i) {
            if (compareAndSetState(0, 1)) {    // state: 0表示无锁   1表示有锁
                //加锁成功，设置锁持有者的线程---> 当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int i) {
            //由于是独占锁，可以不用通过CAS
            setExclusiveOwnerThread(null);
            setState(0);

            return true;
        }

        //是否持有独占锁
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final MSync sync = new MSync();


    //加锁(不成功便会进入阻塞队列等待)
    @Override
    public void lock() {
        sync.acquire(1);
    }

    //加锁，可打断
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    //尝试加锁(一次)
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);

    }


    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return sync.tryAcquireNanos(1, timeUnit.toNanos(l));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
