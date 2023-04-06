package ricardo.cn.AlternateOutput;


import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitSignalOut {
    public static void main(String[] args) throws InterruptedException {
        AwaitSign awaitSign = new AwaitSign(5);
        Condition a = awaitSign.newCondition();
        Condition b = awaitSign.newCondition();
        Condition c = awaitSign.newCondition();


        new Thread(()->{
            awaitSign.print("a",a,b);
        }).start();
        new Thread(()->{
            awaitSign.print("b",b,c);
        }).start();
        new Thread(()->{
            awaitSign.print("c",c,a);
        }).start();

        Thread.sleep(1000);
        System.out.println("开始 ..");
        awaitSign.lock();

        try {
            a.signal();
        }finally {
            awaitSign.unlock();
        }
    }
}

class AwaitSign extends ReentrantLock {
    private int loopNumber;

    public AwaitSign(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String  str, Condition condition,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                condition.await();
                System.out.println(str + " ");
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}


