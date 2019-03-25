package com.dujiajun.courseblock;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.view.WeekView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    //private TextView textCourse;
    //private List<Schedule> scheduleList;
    private CourseManager courseManager;
    private TimetableView timetableView;
    private WeekView weekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //textCourse = findViewById(R.id.text_course);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class))
        );

        timetableView = findViewById(R.id.id_timetableView);
        weekView = findViewById(R.id.id_weekview);
        weekView.curWeek(1)
                .callback(week -> {
                    int cur = timetableView.curWeek();
                    //更新切换后的日期，从当前周cur->切换的周week
                    timetableView.onDateBuildListener()
                            .onUpdateDate(cur, week);
                    timetableView.changeWeekOnly(week);
                })
                .callback(() -> {

                })
                .isShow(false).showView();

        courseManager = CourseManager.getInstance(getApplicationContext());
        //courseManager.setTimetableView(timetableView);
        //timetableView.data(courseManager.getScheduleList()).curWeek(1).showView();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            courseManager.updateCourseDatabase("2018", CourseManager.SEMESTER.SECOND,
                    schedules -> {
                timetableView.data(schedules).curWeek(1).showView();
                weekView.data(schedules).updateView();
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
