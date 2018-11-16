package com.doovj.momovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.doovj.momovie.notif.NotifPreference;

public class ActivityNotifikasi extends AppCompatActivity {
    Switch reminder, releasetoday;

    private AlarmReceiver alarmReceiver;
    private NotifPreference notifPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        reminder = (Switch)findViewById(R.id.switch1);
        releasetoday = (Switch)findViewById(R.id.switch2);

        notifPreference = new NotifPreference(this);
        alarmReceiver = new AlarmReceiver();

        boolean dailystate = notifPreference.getDailyReminder();
        reminder.setChecked(dailystate);

        boolean releasestate = notifPreference.getReleaseToday();
        releasetoday.setChecked(releasestate);

        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    alarmReceiver.setDailyReminder(ActivityNotifikasi.this, AlarmReceiver.TYPE_DAILY_REMINDER);
                    Toast.makeText(ActivityNotifikasi.this, "Daily Reminder diaktifkan", Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.stopNotif(ActivityNotifikasi.this, AlarmReceiver.TYPE_DAILY_REMINDER);
                }
                notifPreference.setDailyReminder(b);
            }
        });

        releasetoday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean becek) {
                if (becek) {
                    alarmReceiver.setReleaseToday(ActivityNotifikasi.this, AlarmReceiver.TYPE_RELEASE_TODAY);
                    Toast.makeText(ActivityNotifikasi.this, "Release Notification diaktifkan", Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.stopNotif(ActivityNotifikasi.this, AlarmReceiver.TYPE_RELEASE_TODAY);
                }
                notifPreference.setReleaseToday(becek);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
