package com.glovoapp.backender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.glovoapp.backender.config.OrderProperties;


@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(OrderProperties.class)

public class DemoApplication implements CommandLineRunner {



@Autowired
private OrderProperties orderProperties;


public static void main(String[] args) throws Exception {
	 ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
//    for (String name: applicationContext.getBeanDefinitionNames()) {
//        System.out.println(name);
//    }
}

@Override
// to test loading the properties
public void run(String... arg0) throws Exception {
	System.out.println( orderProperties);
    System.out.println(String.join(",", orderProperties.getITEMS_REQUIRE_GLOVO_BOX()));
  }

}