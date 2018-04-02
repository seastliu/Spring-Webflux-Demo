package com.seast.demo.routes;

import com.seast.demo.handler.HelloWorldHandler;
import com.seast.demo.handler.UserManageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class Router {

    @Autowired
    private HelloWorldHandler helloWorldHandler;

    @Autowired
    private UserManageHandler userManageHandler;

    @Bean
    public RouterFunction<?> routerFunction(){
        return RouterFunctions.route(RequestPredicates.GET("/hello"),
                    helloWorldHandler::helloWorld)
                .andRoute(RequestPredicates.POST("/register"),
                        userManageHandler::register)
                .andRoute(RequestPredicates.POST("/login"),
                        userManageHandler::login);

    }
}
