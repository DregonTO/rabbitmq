package org.example.rabbitmqspringboot.m2;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Producer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        while (true){
            System.out.print("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            /**
             * AmqpTemplate 默认发送的是持久消息
             */
            amqpTemplate.convertAndSend("task_queue", msg);

            // 如果要发送非持久消息，需要一个消息预处理对象先修改消息属性，然后再进行发送
//            amqpTemplate.convertAndSend("task_queue", (Object) msg, new MessagePostProcessor() {
//                @Override
//                public Message postProcessMessage(Message message) throws AmqpException {
//                    //取出消息属性，对持久属性进行修改
//                    MessageProperties properties = message.getMessageProperties();
//                    properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
//                    return message;
//                }
//            });

        }
    }
}
