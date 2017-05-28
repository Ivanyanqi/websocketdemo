package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Description :
 * Date : 2017/5/27
 * Time : 14:23
 *
 * @author : 仙人球
 */
@Controller
public class WsController {

    private Logger logger  = LoggerFactory.getLogger(WsController.class);

    /**
     *   (1)@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。

         (2)如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息
            因为我们现在处理的 不是 HTTP，所以无法使用 spring 的 HttpMessageConverter 实现 将负载转换为Shout 对象。
            Spring 4.0 提供了几个消息转换器如下：（Attention， 如果是传输json数据的话，定要添加 Jackson jar
            包到你的springmvc 项目中，不然连接不会成功的）
            ByteArrayMessageConvert : application/octet-stream ---->  byte[]
            MappingJackson2MessageConvert application/json ----> java object
            StringMessageConvert   text/plain --- >  string
     * @param message
     * @return
     */

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public String say(String message){
        logger.info("客户端发送的消息 = {}",message);
        return message;
    }


    /**
     *    处理订阅（@SubscribeMapping注解）  :
     *      当收到 STOMP 订阅消息的时候，带有 @SubscribeMapping 注解 的方法将会触发；
     *      其也是通过 AnnotationMethodMessageHandler 来接收消息的
     *    应用场景：
     *      实现 请求-回应模式。在请求-回应模式中，客户端订阅一个目的地，然后预期在这个目的地上 获得一个一次性的 响应
     *      请求-回应模式与 HTTP GET 的全球-响应模式差不多： 关键区别在于， HTTP GET 请求是同步的，
     *      而订阅的全球-回应模式是异步的，这样客户端能够在回应可用时再去处理，而不必等待
     *
     *     A0）这个SubscribeMapping annotation标记的方法，是在订阅的时候调用的，也就是说，
     *        基本是只执行一次的方法，client 调用定义在server 的 该 Annotation 标注的方法，它就会返回结果，不过经过代理
     *     A1）这里的 @SubscribeMapping 注解表明当 客户端订阅 "/app/macro" 主题的时候（"/app"是应用目的地的前缀，
     *        注意，这里没有加springmvc 项目名称前缀）， 将会调用 handleSubscription 方法。它所返回的shout 对象
     *        将会进行转换 并发送回client
     *     A2）SubscribeMapping注解的区别在于： 这里的 Shout 消息将会直接发送给 client，
     *     不用经过 消息代理；但，如果为方法添加 @SendTo 注解的话，那么 消息将会发送到指定的目的地，这样就会经过代理
     *
     *     在应用的任意地方发送消息:
     *       spring 的 SimpMessagingTemplate 能够在应用的任何地方发送消息，不必以接收一条消息为 前提
     *     为目标用户发送消息 :
     *       在使用 spring 和 STOMP 消息功能的时候，有三种方式来利用认证用户:
     *          way1）@MessageMapping and @SubscribeMapping 注解标注的方法 能够使用 Principal 来获取认证用户
     *          way2）@MessageMapping, @SubscribeMapping, and @MessageException 方法返回的值能够以 消息的形式发送给 认证用户
     *          way3）SimpMessagingTemplate 能够发送消息给特定用户
     *
     *
     *
     *
     *
     *
     *
     */

}
