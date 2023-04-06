package ricardo.cn.ReentrantMutex;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 默认不公平,可通过构造函数改变为公平锁
 */
public class FairMutex {

    private static ReentrantLock reentrantLock = new ReentrantLock(true);


}
