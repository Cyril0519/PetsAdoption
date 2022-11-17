package com.petsAdoption.webGateway.filter;

import com.petsAdoption.webGateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final String LOGIN_URL = "http://localhost:8001/api/oauth/toLogin";
    public static final String Authorization = "Authorization";

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取当前请求对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        boolean notNeedAuthorize = this.isNotNeedAuthorize(path);

        if (notNeedAuthorize) {
            return chain.filter(exchange);
        }

        //判断cookie上是否存在jti
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)){
            //拒绝访问,请求跳转
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
            return this.toLoginPage(LOGIN_URL+"?from="+request.getURI(), exchange);

        }

        //判断redis中token是否存在
        String redisToken = authService.getJwtFromRedis(jti);
        if (StringUtils.isEmpty(redisToken)){
            //拒绝访问，请求跳转
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
            return this.toLoginPage(LOGIN_URL+"?from="+request.getURI(), exchange);
        }

        //校验通过 , 请求头增强，放行
        request.mutate().header(Authorization,"Bearer "+redisToken);
        return chain.filter(exchange);
    }

    private Mono<Void> toLoginPage(String login_url, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set("Location", login_url);
        return response.setComplete();
    }
    @Override
    public int getOrder() {
        return 0;
    }

    private boolean isNotNeedAuthorize(String path) {
        String[] notNeedAuthorizePaths = new String[]{
                "/api/oauth/login",
                "/api/oauth/toLogin",
                "/api/oauth/register",
                "/api/oauth/toRegister",
                "/api/oauth/kaptcha",
                "/api/user/add",
                "/api/user/existUsername",
                "/api/pets/**",  // todo 待修改
                "/api/petDetail/getById**", // todo 待修改
                "/api/search",
                "/api/region/**",
                "/api/ad/**"
        };

        for (String notNeedAuthorizePath : notNeedAuthorizePaths) {

            if (path.startsWith(notNeedAuthorizePath.replace("**", ""))) { // 正则替换
                return true;
            }
        }
        return false;
    }


}
