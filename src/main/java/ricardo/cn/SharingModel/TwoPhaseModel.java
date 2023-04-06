package ricardo.cn.SharingModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 利用volatile实现两阶段终止模式  -- 利用一个线程优雅地打断另一个线程
 */
public class TwoPhaseModel {

    public static void main(String[] args) throws InterruptedException {
        InterruptThread thread = new InterruptThread();

        thread.start();

        Thread.sleep(5000);

        thread.stop();
    }

}


@Slf4j
class InterruptThread {
    private Thread monitor;
    private volatile boolean stop = false;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("料理后事");
                    break;
                }

                try {
                    Thread.sleep(500);
                    System.out.println("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    //在sleep时被打断会清空打断标记  sleep出现异常会清空打断标志
//                    currentThread.interrupt();

                }
            }
        });

        monitor.start();
    }


    public void stop() {
        stop = true;
        monitor.interrupt();  //打断sleep
    }
}