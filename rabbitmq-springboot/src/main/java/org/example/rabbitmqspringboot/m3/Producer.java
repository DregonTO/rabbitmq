package org.example.rabbitmqspringboot.m3;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * 订阅发布模式
 */
@Component
public class Producer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        while (true){
            System.out.print("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            //向交换机 发送消息
            amqpTemplate.convertAndSend("logs", "", msg);

        }
    }
}
