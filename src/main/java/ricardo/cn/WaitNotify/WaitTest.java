package ricardo.cn.WaitNotify;

public class WaitTest {

    static final Object lock = new Object();

    public static void main(String[] args) {
        synchronized (lock){
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
