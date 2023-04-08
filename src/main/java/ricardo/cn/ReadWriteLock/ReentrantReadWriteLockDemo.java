package ricardo.cn.ReadWriteLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：
 * 在实际的工作中，读操作往往比写操作更加频繁，因此可以利用读读并发，读写互斥
 */
@Slf4j
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        DataContainer container = new DataContainer();
        new Thread(()->{
            container.read();
        },"t1").start();
        new Thread(()->{
            container.read();
        },"t2").start();
    }
}
@Slf4j
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    public DataContainer() {
        this.data = data;
    }
    //q: 这段代码是什么意思?
    public Object read() {
        log.debug("获取读锁");
        readLock.lock();
        try {
            log.debug("读取");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return data;
        } finally {
            log.debug("释放读锁");
            readLock.unlock();
        }

    }

    public void write() {
        log.debug("获取写锁...");
        writeLock.lock();
        try {
            log.debug("写入");
        } finally {
            log.debug("释放写锁...");
            writeLock.unlock();
        }
    }


}