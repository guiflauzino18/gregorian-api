package com.gregorian.api.Interfaces;

import java.time.LocalTime;
import java.util.Calendar;

public interface DateConverterInterface {
    Calendar getDateCurrent();
    Calendar toCalendar(String data);
    Calendar toCalendar(String date, String time);
    LocalTime getHour(String hour);
}
