package org.example.bank2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bank2Application {

    public static void main(String[] args) {
        String config = "bank2service.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        //容器启动
        ((ClassPathXmlApplicationContext)ctx).start();
    }

}

