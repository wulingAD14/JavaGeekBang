package week5.school;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;

public class School {

    @Autowired(required = true)
    Klass class1;

    @Resource(name="student001")
    Student student001;

    public String getStudent() {
        return class1.getName();
    }

    public static void main(String[] args){
        System.out.println("----------start----------");
    }


}
