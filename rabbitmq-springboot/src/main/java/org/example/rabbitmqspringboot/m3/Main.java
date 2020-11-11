package org.example.rabbitmqspringboot.m3;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * 订阅发布模式
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //创建交换机
    @Bean
    public FanoutExchange logsExchange(){
        return new FanoutExchange("logs",false,false);
    }

    @Autowired
    private Producer producer;

    @PostConstruct  //spring完全加载完成，扫描创建完所有对象，并完成所有注入操作后执行
    public void test() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                producer.send();
//            }
//        }).start();

        new Thread(() -> producer.send()).start();

    }

}
