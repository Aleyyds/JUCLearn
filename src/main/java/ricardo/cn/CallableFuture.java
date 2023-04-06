package ricardo.cn;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("running...");
                Thread.sleep(1000);
                return 1;
            }
        });
        new Thread(task).start();
        System.out.println(task.get());


    }

}
