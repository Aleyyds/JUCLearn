package ricardo.cn.AlternateOutput;

import lombok.extern.slf4j.Slf4j;

/**
 * 三个线程交替输出
 */
@Slf4j
public class SyncOutput {

    public static void main(String[] args) {

        WaitNo waitNo = new WaitNo(1, 100);
        new Thread(() -> {
            waitNo.print(1, 2);
        }).start();
        new Thread(() -> {
            waitNo.print(2, 3);
        }).start();
        new Thread(() -> {
            waitNo.print(3, 1);

        }).start();
    }
}

@Slf4j
class WaitNo {
    private int count;
    private int flag;
    private int loopNumber;

    public void print(int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(++count + " ");
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    public WaitNo(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}