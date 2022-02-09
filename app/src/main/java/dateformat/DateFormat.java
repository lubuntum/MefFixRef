package dateformat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    java.text.DateFormat simpleDateFormat;
    public DateFormat(){
        this.simpleDateFormat =  SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD);
    }

    public String getCurrentDateWithFormat(){
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}
