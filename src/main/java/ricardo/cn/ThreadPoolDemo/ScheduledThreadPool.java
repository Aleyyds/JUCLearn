package ricardo.cn.ThreadPoolDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 带有任务调度功能的线程池
 * 在核心线程数足够的情况下，某个线程执行耗时多长，并不会影响其他线程
 * 一个线程出现异常，也不会影响其他线程
 *
 * 正确处理线程池中的异常 ：
 * 1、使用try-catch代码块，获取异常信息
 * 2、使用Future获取异常信息：如果没有异常则返回正常的信息，否则返回异常信息
 */
@Slf4j
public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.schedule(()->{
            log.debug("task1");
            int i = 1/0;
        },1, TimeUnit.SECONDS);

        pool.schedule(()->{
            log.debug("task2");
        },1, TimeUnit.SECONDS);

        pool.shutdown();

    }
}
