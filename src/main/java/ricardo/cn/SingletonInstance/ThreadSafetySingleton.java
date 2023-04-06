package ricardo.cn.SingletonInstance;

/**
 * 线程安全的单例
 */
public class ThreadSafetySingleton {
}

//懒汉式
final class LazySingleton {
    private LazySingleton() {
    }

    private static volatile LazySingleton INSTANCE = null;

    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }

    //防止反序列化破坏单例
    public Object readResolve() {
        return INSTANCE;
    }
}

final class HungrySingleton{
    private HungrySingleton(){}

    private static final HungrySingleton SINGLETON = new HungrySingleton();

    public static HungrySingleton getInstance(){
        return SINGLETON;
    }
}

//枚举单例  不能使用反射破坏单例      枚举默认实现了序列化接口，但是枚举防止了反序列化破坏
// 饿汉式
enum EnumSingleton{
    INSTANCE
}


// 懒汉式
class InnerClassSingleton{
    private InnerClassSingleton(){}

    private static class LazyHolder{
        static final InnerClassSingleton INNER_CLASS_SINGLETON = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance(){
        return LazyHolder.INNER_CLASS_SINGLETON;
    }
}