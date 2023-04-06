package ricardo.cn.ReentrantMutex;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Timeout {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("尝试获取锁...");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)){
                    log.debug("获取不到锁...");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("获取不到锁...");
                return;
            }
            try {
                log.debug("获取到锁");
            }finally {
                lock.unlock();
            }
        },"t1");
        lock.lock();
        log.debug("获取到锁");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock.unlock();
        log.debug("释放了锁");
    }

}
