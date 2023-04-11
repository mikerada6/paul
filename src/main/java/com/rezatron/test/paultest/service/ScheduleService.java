package com.rezatron.test.paultest.service;

import com.rezatron.test.paultest.entity.Schedule;
import com.rezatron.test.paultest.entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public
class ScheduleService {

  public
  List<String> generateSchedules() {

    List<String> options = Arrays.asList( "W1",
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
                                          "E6" );
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
    List<Schedule> schedules = new ArrayList<>();
    schedules = generateSchedules( new Schedule(),
                                   4 );
    Pattern pattern = Pattern.compile( ".*E.E.*" );


    schedules = schedules.stream().map( s -> s.toString() ).filter( pattern.asPredicate() )
                         .map( a -> new Schedule( a ) ).collect( Collectors.toList() );

    List<Student> students = new ArrayList<>();
    for (int i = 0; i < 26; i++) {
      students.add( new Student( "Student " + (1 + i) ) );
    }
    List<Schedule> temp = new ArrayList<>();
    List<Schedule> best = findBestSchedule( schedules,
                                            temp,
                                            24 );
    System.out.println("DONE");
    return null;
  }

  public
  boolean willAddBeOK(List<Schedule> schedules, Schedule schedule)
  {
    Integer one = schedules.stream().map( s -> s.getSchedule().get( 0 ).equals( schedule.get( 0 ) ) ? 1 : 0 ).reduce( 0,
                                                                                                                      Integer::sum );
    Integer two = schedules.stream().map( s -> s.getSchedule().get( 1 ).equals( schedule.get( 1 ) ) ? 1 : 0 ).reduce( 0,
                                                                                                                      Integer::sum );
    Integer three =
      schedules.stream().map( s -> s.getSchedule().get( 2 ).equals( schedule.get( 2 ) ) ? 1 : 0 ).reduce( 0,
                                                                                                          Integer::sum );
    Integer four =
      schedules.stream().map( s -> s.getSchedule().get( 3 ).equals( schedule.get( 3 ) ) ? 1 : 0 ).reduce( 0,
                                                                                                          Integer::sum );

    if(one>=3 || two>=3 || three>=3 || four>=3)
      return false;
    int count =0;
    if(one>=2)
      count++;
    if(two>=2)
      count++;
    if(three>=2)
      count++;
    if(four>=2)
      count++;
    return count<1;
  }

  public
  List<Schedule> findBestSchedule(List<Schedule> schedules, List<Schedule> usedSchedules, int goalSize)
  {
    if (usedSchedules.size() == goalSize) {
      System.out.println( usedSchedules );
      return usedSchedules;
    }
    List<Schedule> possibleSchedules = new ArrayList<>();
    for (Schedule s : schedules) {
      boolean add = true;
      for (Schedule us : usedSchedules) {
        int count = s.score( us );
        if (count >= 2) {
          add = false;
          break;
        }
      }
      if (add && willAddBeOK(usedSchedules,s)) {
        possibleSchedules.add( s );
      }
    }
    if (possibleSchedules.size() == 0) {
//      System.out.print( "." );
      return possibleSchedules;
    }
    for (Schedule ps : possibleSchedules) {
      List<Schedule> nextSchedules = new ArrayList<>();
      List<Schedule> nextUsedSchedules = new ArrayList<>();
      for(Schedule x: usedSchedules)
      {
        nextUsedSchedules.add(x);
      }
      nextUsedSchedules.add(ps);
      for(Schedule x: possibleSchedules)
      {
        nextSchedules.add(x);
      }
      findBestSchedule( nextSchedules,
                        nextUsedSchedules,
                        goalSize );

    }
    return null;
  }

  public
  List<Schedule> findBestSchedule(List<List<Schedule>> schedules)
  {
    int t = 0;
    List<Schedule> bestSchedule = new ArrayList<>();
    int best = Integer.MAX_VALUE;
    for (int i = 0; i < schedules.size(); i++) {
      if (++t % 10000 == 0) {
        System.out.print( "." );
      }
      int count = 0;
      List<Schedule> list = schedules.get( i );
      for (int x = 0; x < list.size(); x++) {
        Schedule s = list.get( x );
        for (int y = 0; y < list.size(); y++) {
          count += s.score( list.get( y ) );
        }
      }
      if (count < best) {
        bestSchedule = list;
        best = count;
        System.out.println( "Best Score : " + best );
      }
    }
    return bestSchedule;
  }

  List<List<Schedule>> generateRandomSchedules(List<Schedule> schedules, int studentCount, int generateCount)
  {
    List<List<Schedule>> masterList = new ArrayList<>();
    for (int i = 0; i < generateCount; i++) {
      Random rand = new Random();
      List<Schedule> possibleSchedules = new ArrayList<>();
      for (int p = 0; p < studentCount; p++) {
        Schedule randomElement = schedules.get( rand.nextInt( schedules.size() ) );
        possibleSchedules.add( randomElement );
      }
      masterList.add( possibleSchedules );
    }
    return masterList;
  }

  List<Student> generateSchedules(List<Student> students, List<Schedule> schedules) {
    List<Schedule> usedSchedules = new ArrayList<>();
    Random rand = new Random();
    for (Student s : students) {
      List<Schedule> possibleSchedules = getGoodSchedules( usedSchedules,
                                                           schedules );
      System.out.println( possibleSchedules.size() );
      Schedule randomElement = possibleSchedules.get( rand.nextInt( possibleSchedules.size() ) );
      usedSchedules.add( randomElement );
    }
    for (Schedule schedule : usedSchedules) {
      System.out.println( schedule.toStringWithTabs() );
    }
    return students;
  }

  private
  List<Schedule> getGoodSchedules(List<Schedule> usedSchedules, List<Schedule> schedules) {
    List<Schedule> goodSchedules = new ArrayList<>();

    List<String> options = Arrays.asList( "W1",
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
                                          "E6" );


    for (Schedule s : schedules) {
      boolean okToAdd = true;
      for (Schedule t : usedSchedules) {
        if (s.score( t ) > 1) {
          okToAdd = false;
          break;
        }
      }
      if (okToAdd) {
        goodSchedules.add( s );
      }
    }
    for (int i = 0; i < 4; i++) {
      for (String option : options) {
        int finalI = i;
        long count = usedSchedules.stream().map( c -> c.getSchedule().get( finalI ) ).filter( c -> c.equals( option ) )
                                  .count();
        if (count >= 2) {
          int finalI1 = i;
          goodSchedules = goodSchedules.stream().filter( s -> !s.getSchedule().get( finalI1 ).equals( option ) )
                                       .collect( Collectors.toList() );
        }
      }
    }
    return goodSchedules;
  }

  private
  List<Schedule> generateSchedules(Schedule schedule, int size)
  {
    List<Schedule> newSchedules = new ArrayList<>();
    if (schedule.size() == size) {
      newSchedules.add( schedule );
      return newSchedules;
    }
    List<String> toAdd = schedule.getClassesToAdd();
    for (String t : toAdd) {
      Schedule temp = new Schedule( schedule );
      temp.add( t );
      newSchedules.addAll( generateSchedules( temp,
                                              size ) );

    }

    return newSchedules;
  }
}
