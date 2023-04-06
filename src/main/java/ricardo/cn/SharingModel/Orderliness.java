package ricardo.cn.SharingModel;

/**
 * 在不改变程序运行的结果前提下，CPU指令的各个阶段可以通过重排序来实现CPU指令级别的并行，提高程序的运行效率
 * 。指令重排序在单线程的情况下没有问题，但是在多线程的情况下会出现问题   可以利用synchronized 和 volatile来实现禁止指令重排序
 * <p>
 * 写屏障保证在该屏障之前的，对共享变量的改动，都同步到主存中
 * 而读屏障保证在该屏障之后，对共享变量的读取，加载的是主存中最新的数据
 */
public class Orderliness {

}
