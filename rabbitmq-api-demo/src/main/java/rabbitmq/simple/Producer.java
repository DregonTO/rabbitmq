package rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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

        //定义队列 helloword ,服务器不存在这个队列会新建队列，反之会用已存在的队列
        //在这里告诉服务器，让服务器准备好这个队列
        channel.queueDeclare(
                "helloworld"
                , false
                , false
                , false
                , null);

        //发送消息
        channel.basicPublish("", "helloworld", null, "no word".getBytes());

        System.out.println("简单 消息已发送");
        channel.close();
    }
}
