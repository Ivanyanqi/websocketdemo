package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Description :  @EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，
 *                这时候控制器（controller）开始支持@MessageMapping,就像是使用@requestMapping一样
 *
 *                spring 的消息功能是基于消息代理构建的，因此我们必须要配置一个 消息代理 和 其他的一些消息目的地
 * Date : 2017/5/27
 * Time : 14:08
 *
 * @author : 仙人球
 */

@Configuration
// 开启对webSocket的支持,
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    //注册一个Stomp的节点（endpoint）,并指定使用SockJS协议
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //连接地址
        stompEndpointRegistry.addEndpoint("/stomp").withSockJS();  // 连接端点
    }

    //配置消息代理（MessageBroker）
    //配置了一个 简单的消息代理。如果不重载，默认case下，会自动配置一个简单的 内存消息代理，用来处理 "/topic" 为前缀的消息。
    //  但经过重载后，消息代理将会处理前缀为 "/topic" and "/queue" 消息
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         *   启用了 STOMP 代理中继功能
         *   enableStompBrokerRelay()
         *   默认情况下： STOMP 代理中继会假设 代理监听 localhost 的61613 端口，
         *   并且 client 的 username 和password 均为 guest。当然你也可以自行定义
         *
         *   这里不再模拟STOMP 代理的功能，而是由 代理中继将消息传送到一个 真正的消息代理来进行处理(如RabbitMQ 或 ActiveMQ)
         */
        //registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/ws");  // 访问地址: /ws/+ mapping
    }
}
