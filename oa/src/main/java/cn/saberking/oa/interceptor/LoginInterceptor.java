package cn.saberking.oa.interceptor;

import cn.saberking.oa.util.common.HrmConstants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/7
 * @Description:cn.saberking.oa.interceptor
 * @version:1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute(HrmConstants.USER_SESSION) == null) {
            response.sendRedirect(HrmConstants.LOGIN);
            return false;
        }
        return true;
    }
}
