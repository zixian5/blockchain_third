package com.third.service.blockchain.GlobalHandler;

import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

//@Component
public class LoginInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Integer id = (Integer) request.getSession().getAttribute("id");
        if(id != null)
        {
            return true;
        }
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("code",401);
        map.put("data","no session");
        response.getWriter().write(new Gson().toJson(map));
        System.out.println("no session");
        return false;
    }
}
