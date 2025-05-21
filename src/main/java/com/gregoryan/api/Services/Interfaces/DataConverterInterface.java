package com.gregoryan.api.Services.Interfaces;

import java.util.Calendar;

public interface DataConverterInterface {
    Calendar getDateCurrent();
    Calendar getDateOfBirth(String data);
}
