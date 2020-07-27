package com.hcb.hotchairs.converters;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateConverter
{
    public List<Date> toDateList (Date startDate, Date endDate, int[] dayWeek){
        LocalDate localDateStart = LocalDate.parse(startDate.toString());
        LocalDate localDateEnd = LocalDate.parse(endDate.toString());


        List<LocalDate> dateStartWith = new ArrayList<>();
        DayOfWeek monday = DayOfWeek.MONDAY;
        for(int i = 0; i < 7;i++){
            DayOfWeek currentDay = monday.plus(i);
            if(dayWeek[i] == 1){
                dateStartWith.add(localDateStart.with(TemporalAdjusters.nextOrSame(currentDay)));
            }
        }

        List<Date> requiredDays  = new ArrayList<>();
        for(LocalDate element: dateStartWith){
            LocalDate current = element;
            while (current.compareTo(localDateEnd) <= 0){
                requiredDays.add(Date.valueOf(current));
                current = current.plusDays(7);
            }
        }
        return requiredDays;
    }
}