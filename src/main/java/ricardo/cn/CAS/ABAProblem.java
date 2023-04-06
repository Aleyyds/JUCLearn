package ricardo.cn.CAS;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题  通过AtomicStampedReference 的版本号解决问题
 */
@Slf4j
public class ABAProblem {

    static AtomicStampedReference<String> ref = new AtomicStampedReference("A",0);

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");

        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("{}",stamp);
        other();
        Thread.sleep(1000);
        log.debug("change A->C {}",ref.compareAndSet(prev,"C",stamp,stamp+1));
    }

    public static void other(){
        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("{}",stamp);

            log.debug("change A->B {}",ref.compareAndSet(ref.getReference(),"B", stamp, stamp+1));
        });
        new Thread(()->{
            int stamp = ref.getStamp();
            log.debug("{}",stamp);

            log.debug("change B->A {}",ref.compareAndSet(ref.getReference(),"A",stamp, stamp+1));
        });
    }


}
