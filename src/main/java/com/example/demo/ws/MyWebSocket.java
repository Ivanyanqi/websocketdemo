package com.example.demo.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 *  第一种 ： 原始的方法
 * Description :  使用 ServerEndpoint 注释的类必须有一个公共的无参数构造函数
 * Date : 2017/5/27
 * Time : 11:06
 *                   注 ：要将此类加入到spring容器中
 *
 *                   使用spring-boot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，但在spring-boot中连容器都是spring管理的
 *                   虽然@Component默认是单例模式的，但spring-boot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来
 * @author : 仙人球
 */



@ServerEndpoint(value = "/ws/test")
@Component
public class MyWebSocket {

    private Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    private Session session;  // webSocket的会话，存在于整个webSocket生命周期中

    /**
     *  线程安全的set集合，用于存放，每一个webSocket连接，本对象的实例
     */
    private static CopyOnWriteArraySet<MyWebSocket> connections = new CopyOnWriteArraySet<>();

    /**
     *  webSocket 连接的监听方法
     * @param session  本次连接的会话bean
     * @param endpointConfig webSocket端点的设置
     */
    @OnOpen
    public void open(Session session, EndpointConfig endpointConfig){
        logger.info("本次连接对象 = {}",this.toString());
        logger.info("本次的会话id = {}",session.getId());
        this.session = session;
        connections.add(this);  // 将连接对象加入到集合中
    }

    /**
     *   webSocket 关闭连接时的监听方法
     * @param session 本次连接的会话bean
     * @param closeReason 关闭的理由
     */
    @OnClose
    public void close(Session session, CloseReason closeReason){
        logger.info("连接会话关闭 = {}" , session.getId());
        connections.remove(this);
        logger.info("关闭的连接的原因 = {} {}",closeReason.getCloseCode(),closeReason.toString());
    }

    /**
     *  连接出错的监听
     * @param session 本次连接的会话bean
     * @param throwable 异常信息
     */
    @OnError
    public void error(Session session,Throwable throwable){
        logger.error(" {} 出错了，错误原因 = {}",session.getId(),throwable.getMessage());
    }

    /**
     * 监听客户端的消息  如果返回值为void ，可以使用标准的send() 方法，发送消息到客户端，有返回值，直接返回到当前的连接客户端
     * @param message
     * @param session
     * @return
     */
    @OnMessage
    public void message(String message,Session session){
        sendMessage(message);
    }

    /**
     * 普通方法，用于发送消息到客户端
     */
    public void sendMessage(String message){
        //获取所有客户端
        for(MyWebSocket connection : connections){
            try {
                connection.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
