package ricardo.cn.parkdemo;

import java.util.concurrent.locks.LockSupport;

/**
 * park:让线程进入暂停状态,打断后不会清空打断标记
 * 打断标记为true时，会让park失效
 */
public class TestPark {
    public static void main(String[] args) {
        try {
            parked();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parked() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("park...");
            LockSupport.park();
            System.out.println("unparked....");
            System.out.println("打断状态:" + Thread.currentThread().isInterrupted());
        }, "t1");

        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }
}
