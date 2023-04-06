package ricardo.cn.SharingModel;

import lombok.extern.slf4j.Slf4j;

/**
 * Java内存模型
 * <p>
 * 1、原子性-保证CPU指令不受到线程上下文切换的影响
 * 2、可见性-保证指令不受到CPU缓存的影响
 * 3、有序性-保证指令不受到CPU指令并行优化的影响
 *
 *
 */
@Slf4j
public class JMM {
    /**
     * volatile保证共享变量在多个线程中的可见性
     * 注:它可以修饰成员变量和静态变量，不可以修饰局部变量
     */
     static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (run) {
                System.out.println();
            }
        }).start();

        Thread.sleep(1000);
        log.debug("停止");
        run = false; // 不可见性
    }
}
