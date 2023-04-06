package ricardo.cn.TwoPhaseTermination;

/**
 * 两阶段终止模式,优雅的终止线程
 */
public class TwoPhaseTerminationInterrupt {
    public static void main(String[] args) throws InterruptedException {
        InterruptThread interruptThread = new InterruptThread();


        interruptThread.start();
        Thread.sleep(3500);
        interruptThread.stop();
    }

}

class InterruptThread{
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            while (true){
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()){
                    System.out.println("料理后事");
                    break;
                }

                try {
                    Thread.sleep(500);
                    System.out.println("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    //在sleep时被打断会清空打断标记  sleep出现异常会清空打断标志
                    currentThread.interrupt();

                }
            }
        });

        monitor.start();
    }


    public void stop(){
        monitor.interrupt();
    }
}
