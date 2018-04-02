package com.seast.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserManageHandler {

    @Autowired
    private ReactiveRedisConnection connection;

    public Mono<ServerResponse> register(ServerRequest request) {
        Mono<Map> body = request.bodyToMono(Map.class);
        return body.flatMap(map -> {
            String username = (String) map.get("username");
            String password = (String) map.get("password");
            return connection.stringCommands()
                    .set(ByteBuffer.wrap(username.getBytes()),
                            ByteBuffer.wrap(password.getBytes()));
        }).flatMap(aBoolean -> {
            Map<String, String> result = new HashMap<>();
            if (aBoolean){
                result.put("message", "successful");
                return ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(result));
            }else {
                result.put("message", "failed");
                return ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(result));
            }
        });
    }

    public Mono<ServerResponse> login(ServerRequest request){
        Mono<Map> body = request.bodyToMono(Map.class);
        return body.flatMap(map -> {
            String username = (String) map.get("username");
            String password = (String) map.get("password");
            return connection.stringCommands().get(ByteBuffer.wrap(
                    username.getBytes())).flatMap(byteBuffer -> {
                byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes, 0, bytes.length);
                String pwd = null;
                try {
                    pwd = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> result = new HashMap<>();
                if (pwd == null || !pwd.equals(password)){
                    result.put("message", "账号或密码错误");
                    return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(BodyInserters.fromObject(result));
                }else {
                    result.put("message", "登录成功");
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .body(BodyInserters.fromObject(result));
                }
            });
        });
    }
}
