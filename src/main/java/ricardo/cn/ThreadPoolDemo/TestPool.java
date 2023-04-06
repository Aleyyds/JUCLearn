package ricardo.cn.ThreadPoolDemo;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程池阻塞队列
 *
 * 拒绝策略:
 *  1、死等
 *  2、设置超时时间
 *  3、让调用者抛出异常
 *  4、让调用者自己另外开辟一条线程执行任务
 *  5、让调用者放弃任务
 */
@Slf4j
public class TestPool {
    private BlockingQueue<Runnable> taskQueue;
    private HashSet<Worker> workers = new HashSet<>();

    private int coreSize;   //核心线程数
    private long timeout;   //获取任务的超时时间
    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    //执行任务
    public void execute(Runnable task) {
        //当任务数没有超过 coreSize时，直接交给worker对象执行
        //如果任务数超过coreSize时，加入任务队列暂存
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("🇨🇳 新增worker{},{}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                log.debug("🇨🇳 加入任务队列{}", task);
//                taskQueue.put(task);

                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    public TestPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        //执行任务
        @Override
        public void run() {
            //1、当task不为空，执行任务
            //2、当task执行完毕，再接着从任务队列获取任务并执行
            while (task != null || (task = taskQueue.take()) != null) {
                try {
                    log.debug("🇬🇹正在执行...{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                workers.remove(this);
            }
        }
    }
}

//拒绝策略
@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}
@Slf4j
class BlockingQueue<T> {

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    private Deque<T> queue = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    //生产者条件变量
    private Condition fullWaitSet = lock.newCondition();

    //消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();


    //容量
    private int capacity;

    //带有超时的阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            //将超时时间统一转换为纳秒
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    //emptyWaitSet.awaitNanos 返回剩余时间
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    //阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    //阻塞添加
    public void put(T element) {
        lock.lock();
        while (queue.size() == capacity) {
            try {
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.addLast(element);
                emptyWaitSet.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }


    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            //判断队列是否已满
            if (queue.size() == capacity) {
                rejectPolicy.reject(this,task);
            } else {
                //有空闲
                log.debug("加入任务队列:{}",task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }

        } finally {
            lock.unlock();
        }
    }
}
