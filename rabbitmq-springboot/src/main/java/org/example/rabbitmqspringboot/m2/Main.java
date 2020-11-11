package org.example.rabbitmqspringboot.m2;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public Queue helloworldQueue(){
        return new Queue("helloworld",false);   //durable 默认true 队列持久化
    }

    @Autowired
    private Producer producer;

    @PostConstruct  //spring完全加载完成，扫描创建完所有对象，并完成所有注入操作后执行
    public void test(){
        producer.send();
    }

}
