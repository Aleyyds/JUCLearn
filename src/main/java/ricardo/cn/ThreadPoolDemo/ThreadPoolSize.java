package ricardo.cn.ThreadPoolDemo;

/**
 * 1、线程池过小会导致程序不能充分地利用系统资源，容易导致饥饿
 * 2、线程池过大会导致更多的线程上下文切换，占用更多内存
 *
 * 1、CPU密集型运算 --> CPU核数+1
 * 2、I/O密集型运算 --> 核数 * 期望CPU利用率 * 总时间(CPU计算时间 + 等待时间) / CPU计算时间
 *
 */
public class ThreadPoolSize {
}
