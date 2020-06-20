package Negocio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class fechas {

    public String getDateTime(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
