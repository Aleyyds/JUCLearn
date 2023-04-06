package ricardo.cn;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            try {
                while (!Thread.currentThread().isInterrupted()) {

                }
            }catch (Exception e){
                System.out.println("stop");
            }

        }, "t1");

        t1.start();
        Thread.sleep(1000);
        System.out.println("interrupted!");
        t1.interrupt();
    }
}
