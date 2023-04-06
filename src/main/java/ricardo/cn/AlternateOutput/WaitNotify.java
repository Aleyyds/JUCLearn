package ricardo.cn.AlternateOutput;

import lombok.extern.slf4j.Slf4j;

/**
 * 顺序化输出
 */
@Slf4j
public class WaitNotify {

    static final Object lock = new Object();
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {

            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("1");
            }

        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notifyAll();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
