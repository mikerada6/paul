package com.rezatron.test.paultest.entity;

public
class Student {

  String name;
  Schedule schedule;

  public
  Student(String name) {
    this.name = name;
    schedule = new Schedule();
  }

  public
  String getName() {
    return name;
  }

  public
  void setName(String name) {
    this.name = name;
  }

  public
  Schedule getSchedule() {
    return schedule;
  }

  public
  void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }
}
