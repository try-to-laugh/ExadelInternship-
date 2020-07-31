package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.exceptions.NoDateException;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Component
public class DateConverter
{
    private final Integer DAYS_IN_WEEK = 7;
    public List<Date> toDateList (Date startDate, Date endDate, int[] dayWeek){
        if (Objects.isNull(dayWeek) || dayWeek.length == 0) {
            if(!startDate.equals(endDate)){
                throw new NoDateException();
            }
            return Collections.singletonList(startDate);
        }

        LocalDate localDateStart = startDate.toLocalDate();
        LocalDate localDateEnd = endDate.toLocalDate();

        List<LocalDate> dateStartWith = new ArrayList<>();
        for (int dayNumber : dayWeek) {
            DayOfWeek currentDay = DayOfWeek.MONDAY.plus(dayNumber);
            dateStartWith.add(localDateStart.with(TemporalAdjusters.nextOrSame(currentDay)));
        }


        List<Date> requiredDays  = new ArrayList<>();
        for (LocalDate element: dateStartWith) {
            LocalDate current = element;
            while (current.compareTo(localDateEnd) <= 0){
                requiredDays.add(Date.valueOf(current));
                current = current.plusDays(DAYS_IN_WEEK);
            }
        }
        return requiredDays;
    }
}