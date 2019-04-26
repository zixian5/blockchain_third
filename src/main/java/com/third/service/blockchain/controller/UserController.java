package com.third.service.blockchain.controller;

import cn.xjfme.encrypt.test.SecurityTestAll;
import cn.xjfme.encrypt.utils.sm2.SM2KeyVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.third.service.blockchain.entity.User;
import com.third.service.blockchain.repository.UserRepository;
import com.third.service.blockchain.response.ResponseCode;
import com.third.service.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/account/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String login(HttpServletRequest request, @RequestBody(required = false)String json)
    {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        System.out.println(jsonObject);
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        System.out.println("----------------------------"+email+"----------------------");
        User u = userRepository.findBYEmail(email);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("data",null);

        if(u == null)
        {
            map.put("code",404);

        }
        else if(!u.getPassword().equals(password))
        {
            map.put("code",403);
        }
        else{
            request.getSession().putValue("id",u.getId());
            map.put("code",200);
        }
     //   map.put("data",username);
     //   System.out.println("----------------------------"+username+"----------------------");
        return new Gson().toJson(map);
    }
    @RequestMapping("/account/getVerificationCode")
    @ResponseBody
    public String getVerificationCode(String email, HttpServletRequest request)
    {
        Map<String,Object> map = new LinkedHashMap<>();
        try {
            String code  = userService.sendVerificationCode(email);
            request.getSession().putValue("code",code);
            map.put("code", ResponseCode.SUCCESS.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", ResponseCode.ERROE.getCode());
        }
        return new Gson().toJson(map);
    }

    @PostMapping(value = "/account/signUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String signUp(HttpServletRequest request, @RequestBody(required = false) String json )
    {
        System.out.println("-----------------"+json+"--------------");
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        String name = jsonObject.get("name").getAsString();
        Integer age = jsonObject.get("age").getAsInt();
        String address = jsonObject.get("address").getAsString();
        String email = jsonObject.get("email").getAsString();
        String verificationCode = jsonObject.get("verificationCode").getAsString();

        Map<String,Object> map = new HashMap<>();
        String code = (String) request.getSession().getAttribute("code");
        if(code == null || !code.equals(verificationCode))
        {
            map.put("code",403);
            return new Gson().toJson(map);
        }
        if(userRepository.findBYEmail(email) != null)
        {
            map.put("code",409);
            return  new Gson().toJson(map);
        }
        User user = new User();
        user.setAddress(address);
        user.setAge(age);
        user.setName(name);
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        System.out.println("--产生SM2秘钥--:");
        SM2KeyVO sm2KeyVO = null;
        try {
            sm2KeyVO = SecurityTestAll.generateSM2Key();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("公钥:" + sm2KeyVO.getPubHexInSoft());
        System.out.println("私钥:" + sm2KeyVO.getPriHexInSoft());
        user.setPrikey(sm2KeyVO.getPriHexInSoft());
        user.setPubkey(sm2KeyVO.getPubHexInSoft());
        userRepository.saveAndFlush(user);
        map.put("code",ResponseCode.SUCCESS.getCode());
        return new Gson().toJson(map);
    }
    @ResponseBody
    @RequestMapping("/personalCenter/getPersonalInfo")
    public String getPersonalInfo(String email)
    {
        User user = userRepository.findBYEmail(email);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("name",user.getName());
        data.put("age",user.getAge());
        data.put("location",user.getAddress());
        data.put("email",user.getEmail());
        data.put("publicKey",user.getPubkey());
        data.put("privateKey",user.getPrikey());

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("data",data);
        map.put("code",200);

        return new Gson().toJson(map);
    }
}
