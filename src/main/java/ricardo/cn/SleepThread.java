package ricardo.cn;

public class SleepThread {
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while (true){
                System.out.println(System.currentTimeMillis());
            }
        },"t1").start();
    }
}
