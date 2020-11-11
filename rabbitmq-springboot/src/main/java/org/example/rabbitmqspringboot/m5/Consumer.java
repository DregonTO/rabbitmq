package org.example.rabbitmqspringboot.m5;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 主题模式
 */
@Component
public class Consumer {

    /**
     * 创建随机队列，非持久，独占，自动删除
     * 把这个队列绑定到logs交换机
     */

    //RabbitListener加到方法上，就不需要再使用@RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            key = {"*.orange.*"},    //路由键 routingKey
            value = @Queue, //@Queue不给任何参数则是 创建随机队列，非持久，独占，自动删除
            exchange = @Exchange(name = "topic_logs",declare = "false"))) //不重复定义交换机
    public void receive1(String msg){
        System.out.println("consumer_1 收到："+msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            key = {"*.*.rabbit","lazy.#"},   //路由键 routingKey
            value = @Queue, //@Queue不给任何参数则是 创建随机队列，非持久，独占，自动删除
            exchange = @Exchange(name = "topic_logs",declare = "false"))) //不重复定义交换机
    public void receive2(String msg){
        System.out.println("consumer_2 收到："+msg);
    }

}
