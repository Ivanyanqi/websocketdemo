package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Description :
 * Date : 2017/5/27
 * Time : 11:04
 *
 * @author : 仙人球
 */
// 注解代表这个类是一个配置类，
@Configuration
public class Config {

    /**
     *  将serverEndpointExporter注入到spring容器中
     * @return
     */

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
