package week7;

import javax.management.MXBean;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.ConstructorProperties;

@Configuration
public class DataSourceConfig {
    @ConstructorProperties(prefix="spring.shardingsphere.datasource")
    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
}
