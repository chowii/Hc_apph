package au.com.holcim.holcimapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateHelper {

    public static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
    public static final SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat apiDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final String unixTimeStampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");
    public static final SimpleDateFormat dMMYYYY = new SimpleDateFormat("d MM yyyy", Locale.getDefault());
    public static final SimpleDateFormat ddMMMYYYY = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    public static final SimpleDateFormat timeDateDisplayFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss a", Locale.getDefault());
    private static final GregorianCalendar calendar = new GregorianCalendar();
    public static final SimpleDateFormat displayDateTimeFormat = new SimpleDateFormat("dd/MM/yy h:mm a", Locale.US);
    private static final String TAG = "DateHelper";

    public static final int TIMELINE_EITHER = 0;
    public static final int TIMELINE_PAST = 1;
    public static final int TIMELINE_FUTURE = 2;

    public static String dateTodMMyyyy(Date d) {
        return dMMYYYY.format(d);
    }

    public static String toApiDate(String s) {
        if (s.isEmpty()) {
            return "";
        }

        try {
            Date date = displayDateFormat.parse(s);
            return apiDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toApiDateTime(String s) {
        if (s.isEmpty()) {
            return "";
        }

        try {
            Date date = displayDateTimeFormat.parse(s);
            return apiDateTimeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Nullable
    public static Date stringToDate(String dateString, String formatString) {
        if(dateString == null || formatString == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void showPicker(Context context, final TextView v) {
        showPicker(context, v);
    }

    public static void showFuturePicker(Context context, final TextView v){
        Calendar today = Calendar.getInstance();
        getDatePickerDialog(context, v, today, TIMELINE_FUTURE, null).show();
    }

    /**
     *
     * @param context
     * @param v
     * @param date must be of the form dd/mm/yyyy
     * @param pastOrFuture must of the form DateHelper.FUTURE or DateHelper.PAST. can be 0 if does not
     *                     past and future allowed
     * @return
     */
    public static DatePickerDialog getDatePickerDialog(Context context, final TextView v, final Calendar date,
                                                       int pastOrFuture , DatePickerDialog.OnDateSetListener pListener){

        DatePickerDialog.OnDateSetListener listener;

        if (pListener==null) {
            listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    v.setText(displayDateFormat.format(calendar.getTime()));
                }
            };
        }else{
            listener = pListener;
        }

        // If our textView isn't update, use the passed date or today's date if nothing is passed
        Calendar cal = null;
        if (date!=null){
            cal = date;
        }
        if (cal==null) {
            cal= Calendar.getInstance();
        }
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        if (!v.getText().toString().isEmpty()) {
            try {
                calendar.setTime(displayDateFormat.parse(v.getText().toString()));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(context, listener, year, month, day);
        if(pastOrFuture!=0){
            Calendar today = Calendar.getInstance();
            if (pastOrFuture== TIMELINE_PAST){
                if (today.compareTo(cal)<=0) // today is before cal
                    dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                else dialog.getDatePicker().setMaxDate(today.getTimeInMillis());
            }
            if (pastOrFuture== TIMELINE_FUTURE){
                if (today.compareTo(cal)<=0)//today is before cal
                    dialog.getDatePicker().setMaxDate(today.getTimeInMillis());
                else dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            }
        }

        return dialog;
    }

    public static void showTimePikerDialog(Context context, final TextView v, final int pastFuture){

        final GregorianCalendar now = new GregorianCalendar();

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.set(year, month,day, hourOfDay,minute);
                switch (pastFuture){
                    case TIMELINE_FUTURE:
                        if (!isDateInPast(calendar)) {
                            v.setText(displayDateTimeFormat.format(calendar.getTime()));
                        } else {

                            v.setText(displayDateTimeFormat.format(now.getTime()));
                        }
                        break;
                    case TIMELINE_PAST:
                        if (isDateInPast(calendar)) {
                            v.setText(displayDateTimeFormat.format(calendar.getTime()));
                        } else {

                            v.setText(displayDateTimeFormat.format(now.getTime()));
                        }
                        break;
                    default:
                        v.setText(displayDateTimeFormat.format(calendar.getTime()));
                }
            }
        };

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(context,listener, hour, minute, false);
        dialog.show();
    }

    public static boolean isDateInPast(GregorianCalendar calendar){
        GregorianCalendar now = new GregorianCalendar();
        return calendar.getTimeInMillis() < now.getTimeInMillis();
    }

    /**
     * Show a date picker and on callback show a time picker
     * @param context
     * @param v
     * @param pastFuture of the Format DateHelper.TIMELINE...
     */
    public static void showDateTimeDialog(final Context context, final TextView v, final int pastFuture){

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                showTimePikerDialog(context, v, pastFuture);
            }
        };

        DatePickerDialog datePickerDialog = getDatePickerDialog(context, v, null, pastFuture, listener);
        datePickerDialog.show();
    }


    /**
     *Takes a text in format d MMM YYYY and return a string formated dd/MM/yyyy
     * @param text must be of the form d MMM YYYY
     * @return
     */
    public static String dateForAPICall (String text){

        if (text.isEmpty()) {
            return "";
        }

        try {
            Date date = displayDateFormat.parse(text);
            return apiDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param text must be of the form dd/mm/yyyy
     * @return
     */
    public static Calendar getCalendarFromString (String text){
        try {
            Date date = apiDateFormat.parse(text);
            return apiDateFormat.getCalendar();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a date set to yesterday at the same time as now and returns the corresponding String
     * in the format d MMM YYYY
     * @return
     */
    public static String getYesterdayAsText(){
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        return displayDateFormat.format(yesterday.getTime());
    }


    /**
     *
     * @param stringDate
     * @return
     */
    public static String getDisplayableStringFromStringDateReceivedFromAPI(String stringDate){
        try {
            Date date = apiDateTimeFormat.parse(stringDate);
            return displayDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes a date in the Format d/MM/yyy (ex 01/02/2016) and returns a string of the format
     * d MMM yyyyy
     * @param date
     * @return a valid date to display or an empty string if a parse exception occurred.
     */
    public static String getDisplayableDateFromAPIDateFormat(String date){
        Date formattedDate;
        try {
            formattedDate = apiDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return displayDateFormat.format(formattedDate);
    }

}
