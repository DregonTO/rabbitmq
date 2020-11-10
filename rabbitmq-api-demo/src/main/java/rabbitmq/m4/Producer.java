package rabbitmq.m4;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂，并设置连接信息
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.64.140");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        Connection conn = connectionFactory.newConnection();//网络连接
        Channel channel = conn.createChannel(); //建立通信通道
        //定义交换机
        channel.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);

        //向交换机发送消息，并携带路由键
        while (true){
            System.out.print("输入：");
            String msg = new Scanner(System.in).nextLine();
            System.out.print("输入路由键：");
            String key = new Scanner(System.in).nextLine();
            /**
             * 第二个参数： 路由键，和绑定键进行匹配，将消息路由到匹配的队列
             * 对于默认交换机这里用队列名来路有消息
             */
            channel.basicPublish("direct_logs", key, null, msg.getBytes());
        }
    }
}
