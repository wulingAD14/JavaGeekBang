package week5.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import week5.school.Student;

@Configuration
public class Klass {

    @Autowired
    private Student student;

    public String getName(){
        return "hello: " + student.getName();
    }

}
