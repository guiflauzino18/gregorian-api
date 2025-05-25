package com.gregoryan.api.Services.Interfaces;

import java.time.LocalTime;
import java.util.Calendar;

public interface DataConverterInterface {
    Calendar getDateCurrent();
    Calendar toCalendar(String data);
    LocalTime getHour(String hour);
}
