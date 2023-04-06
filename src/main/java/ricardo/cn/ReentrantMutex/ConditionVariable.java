package ricardo.cn.ReentrantMutex;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件变量
 */
@Slf4j
public class ConditionVariable {
    static final ReentrantLock ROOM = new ReentrantLock();
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    static Condition waitCigarette = ROOM.newCondition();
    static Condition waitTakeOut = ROOM.newCondition();


    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    waitCigarette.await();
                }

                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                ROOM.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("外卖送到了吗? [{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖！先歇会");
                    waitTakeOut.await();
                }
                log.debug("可以开始干活了？【{}】");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                ROOM.unlock();
            }
        }, "小女").start();

        Thread.sleep(1000);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasTakeout = true;
                waitTakeOut.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送外卖的").start();

        Thread.sleep(1000);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasCigarette = true;
                waitCigarette.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送烟的").start();
    }
}
