package org.example.rabbitmqspringboot.m2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 工作模式
 */
@Component
public class Consumer {
    //RabbitListener加到方法上，就不需要再使用@RabbitHandler
    @RabbitListener(queues = "task_queue")
    public void receive1(String msg){
        System.out.println("consumer_1 收到："+msg);
    }

    @RabbitListener(queues = "task_queue")
    public void receive2(String msg){
        System.out.println("consumer_2 收到："+msg);
    }

}
