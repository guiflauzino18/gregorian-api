package com.gregoryan.api.Services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.gregoryan.api.Services.Interfaces.DataConverterInterface;

@Service
public class DataConverter implements DataConverterInterface{


    @Override
    public Calendar getDateCurrent() {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"));
        return now;
    }

    //Cria um Calendar com o dia da string no formato yyyy-MM-dd;
    @Override
    public Calendar toCalendar(String data) {
        Calendar nascimento = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            nascimento.setTime(dateFormat.parse(data));
        } catch (ParseException e) {
            
            e.printStackTrace();
        }

        return nascimento;
    }

    @Override
    public LocalTime getHour(String hourString) {
        int hour = Integer.parseInt(hourString.split(":")[0]);
        int minute = Integer.parseInt(hourString.split(":")[1]);

        return LocalTime.of(hour, minute);
            
    }

    
    
}
