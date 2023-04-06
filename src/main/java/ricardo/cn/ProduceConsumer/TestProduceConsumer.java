package ricardo.cn.ProduceConsumer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class TestProduceConsumer {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Message(id, "Value" + id));
            }, "生产着" + i).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    Message message = queue.take();
                    System.out.println("已消费消息----->:" + message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}

class MessageQueue {

    private Deque<Message> list = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    System.out.println("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = list.removeFirst();
            System.out.println("已消费一个消息" + message);
            list.notifyAll();
            return message;
        }

    }

    public void put(Message message) {
        synchronized (list) {
            //检查队列是否满了
            while (list.size() == capacity) {
                try {
                    System.out.println("队列已满，生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.addLast(message);
            System.out.println("以生产消息" + message);
            list.notifyAll();

        }

    }


}

final class Message {
    private int id;
    private Object value;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }
}
