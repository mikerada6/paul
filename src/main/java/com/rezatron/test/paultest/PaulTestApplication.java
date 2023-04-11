package com.rezatron.test.paultest;

import com.rezatron.test.paultest.service.ScheduleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public
class PaulTestApplication {

  public static
  void main(String[] args) {
    ScheduleService ss = new ScheduleService();
    ss.generateSchedules();
    SpringApplication.run( PaulTestApplication.class,
                           args );
  }

}
