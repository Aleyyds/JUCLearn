package ricardo.cn.GuardedSuspension;

/**
 * 保护性暂停：用在一个线程等待另一个线程的执行结果
 * <p>
 * 1、有一个结果需要从一个线程传递到另一个线程，让他们关联同一个GuardedObject
 * 2、如果有结果不断从一个线程到另一个线程那么可以使用消息队列
 * 3、JDK中，join的实现、Future的实现就是采用此模式
 */
public class GuardedPause {
    private Object response;


    public Object get() {
        synchronized (this) {
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return response;
        }
    }


    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }

}
