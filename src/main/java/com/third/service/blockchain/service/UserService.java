package com.third.service.blockchain.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


@Service
public class UserService {

    public String sendVerificationCode(String email) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("406964409@qq.com"));
        // 设置收件人地址
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[] { new InternetAddress(email) });
        // 设置邮件标题
        message.setSubject("小小贤Tec验证码");

        String code = getCode(4);

        // 设置邮件内容
        message.setText("验证码为："+code);
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect("406964409@qq.com", "vrsrxcmcwekrcagh");// 密码为刚才得到的授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());

        return code;
    }

    public static String getCode(int n) {
        String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
        char[] ch = new char[n]; //声明一个字符数组对象ch 保存 验证码
        for (int i = 0; i < n; i++) {
            Random random = new Random();//创建一个新的随机数生成器
            int index = random.nextInt(string.length());//返回[0,string.length)范围的int值    作用：保存下标
            ch[i] = string.charAt(index);//charAt() : 返回指定索引处的 char 值   ==》保存到字符数组对象ch里面
        }
        //将char数组类型转换为String类型保存到result
        //String result = new String(ch);//方法一：直接使用构造方法      String(char[] value) ：分配一个新的 String，使其表示字符数组参数中当前包含的字符序列。
        String result = String.valueOf(ch);//方法二： String方法   valueOf(char c) ：返回 char 参数的字符串表示形式。
        return result;
    }



    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        userService.sendVerificationCode("");
    }
}
