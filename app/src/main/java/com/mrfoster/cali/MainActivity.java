package com.mrfoster.cali;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Button[][] buttons;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();

        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Locale locale = new Locale("en", "us");
        TimeZone timeZone = TimeZone.getTimeZone("EST5EDT");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String date = sdf.format(new Date());

        Log.d(TAG, "ABCD " + date);

        populateCalendar(calendar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void populateCalendar(Calendar calendar) {
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentWeekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        int firstDayOfMonth = (currentDayOfWeek - ( (currentDay % 7) - 1)) % 7; // The weekday the month starts on

        if (firstDayOfMonth == 0) { firstDayOfMonth = 7;} // If the month starts on a saturday, this sets it to 7

        populateButtonsArray(firstDayOfMonth, currentYear, currentMonth);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void populateButtonsArray(int firstDayOfMonth, int currentYear, int currentMonth) {
        buttons = new Button[6][7];
        YearMonth yearMonth = YearMonth.of(currentYear, currentMonth+1);
        int daysInMonth = yearMonth.lengthOfMonth();
        int displayedDayCount = 8 - firstDayOfMonth;

        Log.d(TAG, "ABCD The month of " + currentMonth + " has " + daysInMonth + " days");

        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                buttons[week][day] = findViewById(R.id.day1_1 + (week*7) + day);
            }
        }

        for (int day = firstDayOfMonth - 1; day < 7; day++) { // Populating the first week
            buttons[0][day].setText("" + (day - firstDayOfMonth + 2) );
        }

        int week = 1; int day = 0;
        while (displayedDayCount < daysInMonth) {
            buttons[week][day].setText("" + (++displayedDayCount));
            day++;
            if (day==7) { day=0; week++;}
        }

    }
}
