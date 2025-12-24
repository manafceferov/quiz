package com.quiz.util.date;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Component
public class DiffForHumans {

    public String getTime(LocalDateTime date) {
        PrettyTime prettyTime = new PrettyTime(new Locale("az"));
        Date convertedDate = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return prettyTime.format(convertedDate);
    }
}