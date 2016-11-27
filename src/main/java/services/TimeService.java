package services;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuesongwang on 11/21/16.
 */
@Service
public class TimeService {
    public static Calendar calendarDate = Calendar.getInstance();

    public static Date getKDaysBeforeSystemTime(int k){
        calendarDate.add(Calendar.DATE, (-1) * k);
        Date result =  calendarDate.getTime();
        calendarDate.add(Calendar.DATE,  k);
        return result;
    }

    public static Date getCalendarDate() {
        return calendarDate.getTime();
    }

    public static void setCalendarDate(Date d) {
        calendarDate.setTime(d);
    }
}
