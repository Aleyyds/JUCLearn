package ricardo.cn.ImmutableObject;

import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 使用不可变对象来保证线程安全
 */
@Slf4j
public class ImmutableDemo {

    public static void main(String[] args) {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                TemporalAccessor parse = date.parse("1951-04-13");
                log.debug("{}",parse);
            }).start();
        }
    }


}
