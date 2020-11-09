package rabbitmq.m3;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

//发布订阅模式
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
        //channel.exchangeDeclare("log","fanout");
        channel.exchangeDeclare("log", BuiltinExchangeType.FANOUT);

        //向交换机发送消息
        while (true){
            System.out.println("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            /**
             * 参数：
             *      1.交换机名称，简单模式和工作模式使用了默认的direct交换机
             *      2.用来指定队列，对于fanout交换机这个参数无效
             *      3.其他属性
             */
            channel.basicPublish("log", "", null, msg.getBytes());
        }
    }
}
