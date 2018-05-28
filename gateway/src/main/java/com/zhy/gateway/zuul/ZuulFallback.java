package com.zhy.gateway.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhy.gateway.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 路由失败时，回滚
 * 用于在配置文件里所配置的路由
 */
@Component
public class ZuulFallback implements FallbackProvider {
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 设置要回滚的路由
     * 可以是具体的服务名称
     * 也可以是"*"或null，表示所有的服务都支持回滚
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 回滚时响应的内容
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            /**
             * response body
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                ResultVO resultVO = new ResultVO();
                resultVO.setCode(500);
                resultVO.setMsg("The server is unavailable...");
                String vo = objectMapper.writeValueAsString(resultVO);
                return new ByteArrayInputStream(vo.getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //  需要跟getBody()中的编码一致，否则容易出现乱码
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
