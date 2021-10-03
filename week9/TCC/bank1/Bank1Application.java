package org.example.bank1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bank1Application {

    public static void main(String[] args) {
        String config = "bank1service.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        //容器启动
        ((ClassPathXmlApplicationContext)ctx).start();
    }

}

