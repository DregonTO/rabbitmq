package rabbitmq.m3;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

//发布订阅模式
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂，并设置连接信息
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.64.140");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        Connection conn = connectionFactory.newConnection();//网络连接
        Channel channel = conn.createChannel(); //建立通信通道

        //先做三件事：1.定义队列  2.定义交换机  3.绑定
        String queue = UUID.randomUUID().toString();
        channel.queueDeclare(queue, false, true, true, null);
        channel.exchangeDeclare("log", BuiltinExchangeType.FANOUT);
        channel.queueBind(queue, "log", "");

        DeliverCallback deliverCallback=new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String msg = new String(delivery.getBody());
                System.out.println("收到："+msg);
            }
        };
        CancelCallback cancelCallback=new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };

        //从 queue 队列 消费消息
        channel.basicConsume(queue, true, deliverCallback, cancelCallback);
    }
}
