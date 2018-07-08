package jdu.sprintplanner.service;

import jdu.sprintplanner.config.SwaggerConfig;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static java.time.ZoneId.systemDefault;

@UtilityClass
public class Utils {

    static public LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(getZone())
                .toLocalDate();
    }

    static public  ZoneId getZone() {
        //return ZoneId.of("Europe/Paris");//get from application.properties
        return systemDefault();
    }


    static public Date toStartDate(LocalDate dateToConvert) {
        return toDate(dateToConvert);
    }

    static public Date toDate(LocalDate dateToConvert) {
//        DateTimeFormatter.ofPattern("MM-dd-yyyy").format(dateToConvert);
//        return
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(getZone())
                .toInstant());
    }

    static public Date toEndDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atTime(23,59,59)
                .atZone(getZone())
                .toInstant());
    }

}
