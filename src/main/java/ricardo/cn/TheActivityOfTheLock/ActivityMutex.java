package ricardo.cn.TheActivityOfTheLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 活锁
 * 出现在两个线程互相改变对方结束的条件，最后谁也无法结束
 */
@Slf4j
public class ActivityMutex {
    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            //减到0退出循环
            while (count > 0) {
                try {
                    Thread.sleep(200);
                    count--;
                    log.debug("count: {}", count);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (count < 20) {
                try {
                    Thread.sleep(200);
                    count++;
                    log.debug("count:{}", count);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"t2").start();
    }
}
