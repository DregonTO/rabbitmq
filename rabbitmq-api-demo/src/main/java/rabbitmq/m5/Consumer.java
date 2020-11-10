package rabbitmq.m5;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

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

        //1.定义队列    2.定义交换机
        //让服务器自动命名，默认属性：false,true,true 非持久，独占，自动删除
        String queue = channel.queueDeclare().getQueue();
        //定义交换机
        channel.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);

        System.out.println("输入绑定键，用空格隔开");
        String s = new Scanner(System.in).nextLine();
        String[] a = s.split("\\s+");
        for (String key :
                a) {
            channel.queueBind(queue, "topic_logs", key);
        }

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody());
                String key = message.getEnvelope().getRoutingKey();
                System.out.println(key + " - " + msg);
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };

        channel.basicConsume(queue, true, deliverCallback, cancelCallback);

    }
}
