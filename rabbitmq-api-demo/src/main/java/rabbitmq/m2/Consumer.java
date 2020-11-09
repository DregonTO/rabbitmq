package rabbitmq.m2;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//工作模式
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.64.140");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        //建立连接 并创建通道
        Channel channel = factory.newConnection().createChannel();
        //定义队列,两边都定义队列、则不用关心生产者，消费者的启动顺序，谁先启动 谁创建
        //第二个参数 durable 开启持久化 true
        channel.queueDeclare("helloworld", true, false, false, null);
        System.out.println("等待接收数据");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String msg = new String(delivery.getBody());
                System.out.println("收到：" + msg);
                //遍历所有字符，找一个'.'，暂停一秒
                for (int i = 0; i < msg.length(); i++) {
                    if (msg.charAt(i) == '.') {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("----------消息处理完成----------------");
                //发送回执
                //channel.basicAck(回执，是否同事确认之前收到的所有消息)
                //回执：在消息对象中包含一个long长整型整数编码
                //一般指确认当前这一条消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {

            }
        };

        /**
         * 合理分发
         * 开始消费数据之前，把qos设置成1
         */
        channel.basicQos(1);

        //消费数据  第二个参数autoAck 是否自动确认 设置为false，则需要手动确认发送回执
        channel.basicConsume("helloworld", false, deliverCallback, cancelCallback);
    }
}
