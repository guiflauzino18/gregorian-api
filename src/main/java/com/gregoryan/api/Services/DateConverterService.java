package com.gregoryan.api.Services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.gregoryan.api.Services.Interfaces.DateConverterInterface;

@Service
public class DateConverterService implements DateConverterInterface{


    @Override
    public Calendar getDateCurrent() {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"));
        return now;
    }

    //Cria um Calendar com o dia da string no formato yyyy-MM-dd;
    @Override
    public Calendar toCalendar(String data) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            calendar.setTime(dateFormat.parse(data));
        } catch (ParseException e) {
            
            e.printStackTrace();
        }

        return calendar;
    }

    @Override
    public LocalTime getHour(String hourString) {
        int hour = Integer.parseInt(hourString.split(":")[0]);
        int minute = Integer.parseInt(hourString.split(":")[1]);

        return LocalTime.of(hour, minute);
            
    }

    @Override
    public Calendar toCalendar(String date, String time) {
        Calendar calendar = Calendar.getInstance();
        int ano = Integer.parseInt(date.split("-")[0]);
        int mes = Integer.parseInt(date.split("-")[1]);
        int dia = Integer.parseInt(date.split("-")[2]);

        int hora = Integer.parseInt(time.split(":")[0]);
        int min = Integer.parseInt(time.split(":")[1]);

        calendar.set(ano, mes, dia, hora, min);

        return calendar;
        
    }
}
