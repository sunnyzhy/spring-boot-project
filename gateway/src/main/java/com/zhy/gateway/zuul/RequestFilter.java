package com.zhy.gateway.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zhy.gateway.jwt.Jwt;
import com.zhy.gateway.model.User;
import com.zhy.gateway.vo.ResponseVO;
import com.zhy.gateway.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义zuul过滤器
 */
@Component
public class RequestFilter extends ZuulFilter {
    @Autowired
    private Jwt jwt;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResponseVO responseVO;

    /**
     * 定义filter的类型
     * PRE： 过滤器在请求被路由之前调用
     * ROUTING：过滤器将请求路由到微服务
     * POST：过滤器在路由到微服务以后执行
     * ERROR：在其他阶段发生错误时执行该过滤器
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 定义filter的顺序，数字越小表示顺序越高，越先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 表示是否需要执行该filter，true表示执行，false表示不执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * filter需要执行的具体操作
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        String token = httpServletRequest.getHeader("token");
        if (!jwt.checkToken(token)) {
            responseZuulError(requestContext, responseVO.error(401, "token error!"));
            return null;
        }
        User user = jwt.getUserFromToken(token);
        if (user == null) {
            responseZuulError(requestContext, responseVO.error(401, "token error!"));
            return null;
        }
        responseZuul(requestContext);
        return null;
    }

    private void responseZuul(RequestContext requestContext) {
        // 对请求进行路由
        requestContext.setSendZuulResponse(true);
        requestContext.setResponseStatusCode(200);
        requestContext.set("isSuccess", true);
    }

    private void responseZuulError(RequestContext requestContext,
                                   ResultVO resultVO) {
        // 不对请求进行路由
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(resultVO.getCode());
        try {
            requestContext.setResponseBody(objectMapper.writeValueAsString(resultVO));
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestContext.set("isSuccess", false);
    }
}
