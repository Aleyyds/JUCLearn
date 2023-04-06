package ricardo.cn.CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger number = new AtomicInteger();

        System.out.println(number.incrementAndGet());  // ==>1
        System.out.println(number.getAndIncrement());  // ==>1

        System.out.println(number.get());              // ==>2

        System.out.println(number.getAndAdd(5));    // ==>2
        System.out.println(number.addAndGet(5));    // ==>12

    }
}
