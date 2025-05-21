package com.gregoryan.api.Services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    public Calendar getDateOfBirth(String data) {
        Calendar nascimento = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            nascimento.setTime(dateFormat.parse(data));
        } catch (ParseException e) {
            
            e.printStackTrace();
        }

        return nascimento;
    }
    
}
