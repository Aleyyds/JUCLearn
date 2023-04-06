package ricardo.cn.HappensBefore;

/**
 * Happens-before  规定了对共享变量的写操作对其他线程的读操作可见，它是可见性和有序性的一套规则的总结
 * 抛开happens-before规则，JMM并不能保证一个线程对共享变量的写，对于其他线程对该共享变量的读的可见
 *
 * 1、线程解锁m之前对变量的写，对于接下来对m加锁的其他线程对该变量的读可见
 * 2、线程对volatile变量的写，对接下来其它线程对该变量可见
 * 3、线程start前对变量的写，对该线程开始后对该变量的读可见
 * 4、线程结束前对变量的写，对于其它线程得知它结束后的读可见
 */
public class HappensBeforeDemo {
}
