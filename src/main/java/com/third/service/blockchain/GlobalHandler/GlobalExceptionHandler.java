package com.third.service.blockchain.GlobalHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("data", e.getMessage());

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            map.put("code", 404);
        }
        else if(e instanceof MethodArgumentNotValidException || e instanceof MissingServletRequestParameterException)
        {
            map.put("code",400);
        }
        else {
            map.put("code", 500);
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(map);

    }
}
