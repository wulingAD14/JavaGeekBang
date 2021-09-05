package week5.school;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Student.class)
public class KlassConfig {

    @Bean
    public Klass klass(){
        return new Klass();
    }
}
