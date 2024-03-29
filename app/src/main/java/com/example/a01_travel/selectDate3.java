package com.example.a01_travel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class selectDate3 extends AppCompatActivity {

    private Button btn_where, btn_cancel;
    //Variables for ex page
    private TextView tv_where, tv_name1, tv_name2, tv_name3, tv_name4;

    //For Calendar
    private ImageButton btn_changeDate;
    private int currYear, currMonth, currDay;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Button btn_when;
    private TextView tv_warning;
    //String for date
    String dateString1,dateString2,name1,name2,name3,name4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date3);
        TextView datePickerText;



        Calendar calendar;
        datePickerText = findViewById(R.id.date_picker_text);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));


        //Retrieve from xml
        tv_where = findViewById(R.id.tv_where);
        tv_name1 = findViewById(R.id.tv_name1);
        tv_name2 = findViewById(R.id.tv_name2);
        tv_name3 = findViewById(R.id.tv_name3);
        tv_name4 = findViewById(R.id.tv_name4);
        tv_warning = findViewById(R.id.tv_warning);


        //Retrieve destination
        Intent thirdIntent = getIntent();
        String destination = thirdIntent.getStringExtra("destination");
        if(destination != null) {
            tv_where.setText(destination);
        }
        // Retrieve and set names if they exist
        String name1 = thirdIntent.getStringExtra("name1");
        if (name1 != null) {
            tv_name1.setText(name1);
        }

        String name2 = thirdIntent.getStringExtra("name2");
        if (name2 != null) {
            tv_name2.setText(name2);
        }

        String name3 = thirdIntent.getStringExtra("name3");
        if (name3 != null) {
            tv_name3.setText(name3);
        }

        String name4 = thirdIntent.getStringExtra("name4");
        if (name4 != null) {
            tv_name4.setText(name4);
        }


        //Intent for the fourth page


        //Select the period
        ImageButton dateRangePicker = findViewById(R.id.date_range_picker_btn);
        dateRangePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

                builder.setTitleText("Set the Itinerary");

                //Select the Date before
                builder.setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()));

                MaterialDatePicker materialDatePicker = builder.build();

                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");



                //Save Button
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        TimeZone timeZoneKorea = TimeZone.getTimeZone("Asia/Seoul");
                        Calendar calendarStart = Calendar.getInstance(timeZoneKorea);
                        calendarStart.setTimeInMillis(selection.first);
                        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
                        calendarStart.set(Calendar.MINUTE, 0);
                        calendarStart.set(Calendar.SECOND, 0);
                        calendarStart.set(Calendar.MILLISECOND, 0);

                        Calendar calendarEnd = Calendar.getInstance(timeZoneKorea);
                        calendarEnd.setTimeInMillis(selection.second);
                        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
                        calendarEnd.set(Calendar.MINUTE, 59);
                        calendarEnd.set(Calendar.SECOND, 59);
                        calendarEnd.set(Calendar.MILLISECOND, 999);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.KOREA);
                        simpleDateFormat.setTimeZone(timeZoneKorea); // 한국 시간대 설정

                        dateString1 = simpleDateFormat.format(calendarStart.getTime());
                        dateString2 = simpleDateFormat.format(calendarEnd.getTime());
                        datePickerText.setText(dateString1 + " ~ " + dateString2);
                    }
                });
            }
        });



        //Click cancel
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(selectDate3.this, enterGroupMember2.class);
                startActivity(intent2);
            }
        });

        //When clicking the button
        btn_when = findViewById(R.id.btn_when);
        btn_when.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateString2 != null&& dateString1 !=null) {
                    Intent nextIntent = new Intent(selectDate3.this, tripPlan4.class);
                    nextIntent.putExtra("dateString1", dateString1);
                    nextIntent.putExtra("dateString2", dateString2);
                    nextIntent.putExtra("destination",destination);
                    if (name1 != null && !name1.isEmpty()) nextIntent.putExtra("name1", name1);
                    if (name2 != null && !name2.isEmpty()) nextIntent.putExtra("name2", name2);
                    if (name3 != null && !name3.isEmpty()) nextIntent.putExtra("name3", name3);
                    if (name4 != null && !name4.isEmpty()) nextIntent.putExtra("name4", name4);
                    startActivity(nextIntent);
                }
                else {
                    tv_warning.setText("Please Set the Date for trip");
                }
            }
        });
    }
}
