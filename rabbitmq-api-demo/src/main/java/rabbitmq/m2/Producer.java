package rabbitmq.m2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

//工作模式
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.64.140");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        //建立连接 并创建通道
        Channel channel = factory.newConnection().createChannel();
        //定义队列  第二个参数是持久化参数durable ，设置成 持久化true
        channel.queueDeclare("helloworld", true, false, false, null);
        //发送消息
        while (true) {
            System.out.println("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            //设置队列中的消息 持久化
            channel.basicPublish(
                    "",
                    "helloworld",
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    msg.getBytes());
        }
    }
}
