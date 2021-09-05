package week5.beanConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person[name=" + name + ",age=" + age + "]";
    }

    // 默认无参的构造方法
    public Person(){
        super();
    }

    // 有参的构造方法
    public Person(String name, int age){
        super();
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args){

        //XML装配
        String xmlpath = "week5/beanConfig/beans1.xml";
        ApplicationContext context1 = new ClassPathXmlApplicationContext(xmlpath);
        //设值方式输出结果
        System.out.println(context1.getBean("person1"));
        //构造方式输出结果
        System.out.println(context1.getBean("person2"));
        //p命名空间输出结果
        System.out.println(context1.getBean("person3"));

        //注解装配
        ApplicationContext context2 = new AnnotationConfigApplicationContext("week5/beanConfig");
        PersonAction personAction2 = (PersonAction) context2.getBean("PersonAction");
        personAction2.say();

        //自动装配
        xmlpath = "week5/beanConfig/beans3.xml";
        ApplicationContext context3 = new ClassPathXmlApplicationContext(xmlpath);
        PersonAction personAction3 = (PersonAction)context3.getBean("PersonAction");
        personAction3.read();
    }

}
