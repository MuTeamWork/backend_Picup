package com.regulus.filedemo.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitmqConfig {
    //队列名
    public static final String IMG_CONFIRM_QUEUE_NAME = "img.queue";
    public static final String TAG_CONFIRM_QUEUE_NAME = "tag.queue";

    //路由
    public static final String JAVA_ROUTING_KEY = "java";
    public static final String PYTHON_ROUTING_KEY = "py";

    //确认交换机
    public static final String CONFIRM_EXCHANGE = "direct_img";

    // 确认交换机的声明
    @Bean("imgConfirmExchange")
    public DirectExchange imgConfirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE);
    }

    // 队列的声明
    @Bean("imgConfirmQueue")
    public Queue imgConfirmQueue() {
        return new Queue(IMG_CONFIRM_QUEUE_NAME);
    }

    @Bean("tagConfirmQueue")
    public Queue tagConfirmQueue() {
        return new Queue(TAG_CONFIRM_QUEUE_NAME);
    }

    // 绑定关系的声明
    @Bean
    public Binding imgQueueBinding(@Qualifier("imgConfirmQueue") Queue imgConfirmQueue,
                                   @Qualifier("imgConfirmExchange") DirectExchange imgConfirmExchange) {
        return BindingBuilder.bind(imgConfirmQueue).to(imgConfirmExchange).with(PYTHON_ROUTING_KEY);
    }

    @Bean
    public Binding tagQueueBinding(@Qualifier("tagConfirmQueue") Queue tagSelectByCourseConfirmQueue,
                                   @Qualifier("imgConfirmExchange") DirectExchange imgConfirmExchange) {
        return BindingBuilder.bind(tagSelectByCourseConfirmQueue).to(imgConfirmExchange).with(JAVA_ROUTING_KEY);
    }
}
