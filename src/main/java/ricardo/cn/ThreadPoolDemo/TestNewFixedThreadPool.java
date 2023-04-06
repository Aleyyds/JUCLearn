package ricardo.cn.ThreadPoolDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * newFixedThreadPool:
 *      1、核心线程数 == 最大线程数，因此也无需超时时间
 *      2、阻塞队列时无界的，可以放任意数量的任务
 *
 *      适用于任务量已知，相对耗时的任务
 */
@Slf4j
public class TestNewFixedThreadPool {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(()->log.debug("1"));
        pool.execute(()->log.debug("2"));
        pool.execute(()->log.debug("3"));

        pool.shutdown();
    }
}
