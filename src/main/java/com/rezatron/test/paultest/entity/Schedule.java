package com.rezatron.test.paultest.entity;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public
class Schedule {

  private ArrayList<String> schedule;

  public
  Schedule(ArrayList<String> schedule) {
    this.schedule = schedule;
  }

  public
  Schedule() {
    schedule = new ArrayList<>();
  }

  public
  Schedule(String s) {
    schedule = new ArrayList<>();
    if (s.length() == 2) {
      schedule.add( s );
    } else {
      String[] tokens = Iterables.toArray( Splitter.fixedLength( 2 ).split( s ),
                                           String.class );
      List<String> t = Arrays.stream( tokens ).collect( Collectors.toList() );
      schedule.addAll( t );
    }
  }

  public
  Schedule(Schedule _schedule) {
    schedule = new ArrayList<>();
    if (_schedule.getSchedule() != null) {
      schedule.addAll( _schedule.getSchedule() );
    }
  }

  public
  ArrayList<String> getSchedule() {
    return schedule;
  }

  public
  void setSchedule(ArrayList<String> schedule) {
    this.schedule = schedule;
  }

  public
  String get(int i) {
    if (i >= schedule.size()) return null;
    return schedule.get( i );
  }

  public
  void add(String s)
  {
    if (schedule == null) {
      schedule = new ArrayList<>();
    }
    if (s.length() == 2) {
      schedule.add( s );
    } else {
      String[] tokens = Iterables.toArray( Splitter.fixedLength( 4 ).split( s ),
                                           String.class );
      List<String> t = Arrays.stream( tokens ).collect( Collectors.toList() );
      schedule.addAll( t );
    }
  }

  public
  void addAll(List<String> s)
  {
    if (schedule == null) {
      schedule = new ArrayList<>();
    }
    schedule.addAll( s );
  }

  public
  boolean contains(String s)
  {
    if (schedule == null) {
      schedule = new ArrayList<>();
    }
    return schedule.contains( s );
  }

  public
  List<String> getClassesToAdd()
  {
    Map<String, List<String>> conflicts = new HashMap<>();
    conflicts.put( "W1",
                   Arrays.asList( "W3",
                                  "W5" ) );
    conflicts.put( "W2",
                   Arrays.asList( "W4",
                                  "W6" ) );
    conflicts.put( "W3",
                   Arrays.asList( "W1",
                                  "W5" ) );
    conflicts.put( "W4",
                   Arrays.asList( "W2",
                                  "W6" ) );
    conflicts.put( "W5",
                   Arrays.asList( "W1",
                                  "W3" ) );
    conflicts.put( "W6",
                   Arrays.asList( "W2",
                                  "W6" ) );
    conflicts.put( "E1",
                   Arrays.asList( "E3",
                                  "E5" ) );
    conflicts.put( "E2",
                   Arrays.asList( "E4",
                                  "E6" ) );
    conflicts.put( "E3",
                   Arrays.asList( "E1",
                                  "E5" ) );
    conflicts.put( "E4",
                   Arrays.asList( "E2",
                                  "E6" ) );
    conflicts.put( "E5",
                   Arrays.asList( "E1",
                                  "E3" ) );
    conflicts.put( "E6",
                   Arrays.asList( "E2",
                                  "E6" ) );
    List<String> toAdd = new ArrayList<>();
    toAdd.addAll( Arrays.asList( "W1",
                                 "W2",
                                 "W3",
                                 "W4",
                                 "W5",
                                 "W6",
                                 "E1",
                                 "E2",
                                 "E3",
                                 "E4",
                                 "E5",
                                 "E6" ) );
    for (String i : schedule) {
      toAdd.removeAll( conflicts.get( i ) );
      toAdd.remove( i );
    }
    return toAdd;
  }

  public
  int size() {
    return schedule.size();
  }

  public
  String toString()
  {
    StringBuilder sb = new StringBuilder();
    for (String i : schedule) {
      sb.append( i );
    }
    return sb.toString();
  }

  public
  String toStringWithTabs()
  {
    StringBuilder sb = new StringBuilder();
    for (String i : schedule) {
      sb.append( i + "\t" );
    }
    return sb.toString();
  }

  public
  int score(Schedule s)
  {
    ArrayList<String> _Schedule = s.getSchedule();
    int count = 0;
    for (int i = 0; i < schedule.size(); i++) {
      count += schedule.get( i ).equals( _Schedule.get( i ) ) ? 1 : 0;
    }
    return count;
  }
}
