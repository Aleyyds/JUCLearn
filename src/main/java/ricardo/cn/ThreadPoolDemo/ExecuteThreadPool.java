package ricardo.cn.ThreadPoolDemo;

import java.util.concurrent.ExecutorService;

/**
 * ThreadPoolExecutor 使用int的高3位表示线程池的状态
 * 线程池的五种状态
 *
 *  状态名     高三位      接收新任务      处理阻塞队列
 * RUNNING     111          Y               Y
 * SHUTDOWN    000          N               Y       不会接收新的任务，但会处理阻塞队列剩余任务
 * STOP        001          N               N       会中断正在执行的任务，并抛弃阻塞队列任务
 * TIDYING     010                                  任务全部执行完毕，活动线程为0即将进入终结
 * TERMINATED  011                                  终结状态
 */

/**
 * 线程池的构造方法参数
 * 1、corePoolSize：核心线程数
 * 2、maximumPoolSize：最大线程数
 * 3、keepAliveTime：生存时间--针对救急线程
 * 4、unit：时间单位--针对救急线程
 * 5、workQueue：阻塞队列
 * 6、threadFactory：线程工厂--可以为每个线程创建时命名
 * 7、handler：拒绝策略
 */

/**
 * 常见的阻塞队列:
 * 1、AbortPolicy：抛出RejectedExecutionException异常，默认策略
 * 2、让调用者自己运行任务
 * 3、放弃本次任务
 * 4、放弃队列中最早的任务
 * 5、向线程中添加任务时设置超时时间
 * 6、设置拒绝策略链，逐一尝试策略链中的每种拒绝策略
 */
public class ExecuteThreadPool {
}
