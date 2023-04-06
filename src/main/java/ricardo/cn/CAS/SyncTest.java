package ricardo.cn.CAS;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 保护共享资源加锁实现
 */
public class SyncTest {
    public static void main(String[] args) {
        Account unsafe = new AccountCAS(10000);
        Account.demo(unsafe);
        System.out.println(unsafe.getBalance());

    }
}

class AccountCAS implements Account {

    public AccountCAS(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    private AtomicInteger balance;

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void widthDraw(Integer amount) {
        balance.getAndAdd(-1 * amount);
    }
}

class AccountUnsafe implements Account {

    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return this.balance;
        }
    }

    @Override
    public void widthDraw(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }
}


interface Account {

    //获取余额
    Integer getBalance();

    //取款
    void widthDraw(Integer amount);

    /**
     *
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.widthDraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance() + "\ncost:" + (end - start) / 1000_00 + "ms");
    }
}