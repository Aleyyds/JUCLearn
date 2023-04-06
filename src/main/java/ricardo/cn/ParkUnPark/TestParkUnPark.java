package ricardo.cn.ParkUnPark;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * park和unpark与wait()、notify()的区别
 * 1、wait()、notify和notifyAll必须配合monitor一起使用，而park不必
 * 2、park & unpark是以线程为单位来阻塞和唤醒线程，而notify只能随机唤醒一个线程，notifyAll是唤醒所有线程，不是那么准确
 * 3、park & unpark可以先unpark、而wait & notify不能先notify
 *
 *
 */

@Slf4j
public class TestParkUnPark {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            try {
                TimeUnit.SECONDS.sleep(1);
                log.debug("park");
                LockSupport.park();
                log.debug("Resume...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(2);
        log.debug("Unpark");
        LockSupport.unpark(t1);


    }

}
