package com.example.zhxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class ZhxyApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZhxyApplication.class, args);
        for (String beanDefinitionName : run.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
            System.out.println("111111");
        }
    }

}
