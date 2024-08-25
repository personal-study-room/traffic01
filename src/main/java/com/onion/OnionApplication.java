package com.onion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class OnionApplication {

  public static void main(String[] args) {
    SpringApplication.run(OnionApplication.class, args);
  }

}
