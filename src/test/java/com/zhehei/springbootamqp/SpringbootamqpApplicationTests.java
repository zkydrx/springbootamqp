package com.zhehei.springbootamqp;

import com.zhehei.springbootamqp.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootamqpApplicationTests
{

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 1.单播（点对点）
     */
    @Test
    public void contextLoads()
    {
//        rabbitTemplate.send(exchange,routekey,message);
        Map<String,Object> map =new HashMap<>();
        map.put("msg","Hello world!");
        map.put("data", Arrays.asList("java",123,true,999L));
//        rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",map);
        rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Student("zky",20));
    }


    /**
     * 接受消息
     */
    @Test
    public void receiveMessage()
    {
        Object o = rabbitTemplate.receiveAndConvert("atguigu");
        Object o1 = rabbitTemplate.receiveAndConvert("atguigu.news");
        Object o2 = rabbitTemplate.receiveAndConvert("atguigu.emps");
        Object o3 = rabbitTemplate.receiveAndConvert("gulixueyuan.news");

        System.out.println(o.getClass());
        System.out.println(o);
    }


    /**
     * 广播
     */
    @Test
    public void sendMessageFanout()
    {
        rabbitTemplate.convertAndSend("exchange.fanout","",new Student("周星驰",50));
    }



    /**
     * 匹配路由
     */
    @Test
    public void sendMessageTopic()
    {
//        rabbitTemplate.convertAndSend("exchange.topic","atguigu.#",new Student("abc",123));
        rabbitTemplate.convertAndSend("exchange.topic","*.news",new Student("def",456));
    }
}
