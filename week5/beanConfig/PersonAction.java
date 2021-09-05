package week5.beanConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PersonAction")
public class PersonAction {

    @Autowired
    private PersonRead personRead;

    public void read(){
        personRead.read();
    }

    public void say(){
        System.out.println("hello~");
    }
}
