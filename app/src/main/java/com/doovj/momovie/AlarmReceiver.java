package com.doovj.momovie;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.doovj.momovie.notif.NotificationItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY_REMINDER = "DailyReminder";
    public static final String TYPE_RELEASE_TODAY = "ReleaseToday";
    public static final String EXTRA_TYPE = "type";
    private final int NOTIF_ID_DAILY = 100;
    private final int NOTIF_ID_RELEASE = 101;
    private static final String API_KEY = BuildConfig.TMDBKey;
    private String title, message;
    private NotificationCompat.InboxStyle inboxStyle;
    private NotificationItem notificationItem;
    private Notification notification;
    private NotificationManagerCompat manager;
    private final static String GROUP_KEY_MOVIES = "group_key_movies";

    public AlarmReceiver() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        final int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? NOTIF_ID_DAILY : NOTIF_ID_RELEASE;

        if (notifId == NOTIF_ID_DAILY) {
            title = "Momovie";
            showNotification(context, title, notifId);
        } else {
            final PendingResult result = goAsync();
            Thread thread = new Thread() {
                public void run() {
                    AsyncHttpClient client = new AsyncHttpClient();
                    final ArrayList<MovieItems> movieItemses = new ArrayList<>();
                    String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY;

                    client.get(url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                List<NotificationItem> stackNotif = new ArrayList<>();
                                stackNotif.clear();
                                int idNotif = 0;
                                int maxNotif = 2;

                                String result = new String(responseBody);
                                JSONObject responseObject = new JSONObject(result);
                                JSONArray list = responseObject.getJSONArray("results");

                                long today = System.currentTimeMillis();
                                Date resultdate = new Date(today);
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String hariieu = inputFormat.format(resultdate);
                                Date hariini = inputFormat.parse(hariieu);

                                manager = NotificationManagerCompat.from(context);
                                notification = null;

                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject movie = list.getJSONObject(i);
                                    MovieItems movieItems = new MovieItems(movie);
                                    movieItemses.add(movieItems);

                                    String tanggalrilis = movieItemses.get(i).getTanggalrilis();
                                    Date date = inputFormat.parse(tanggalrilis);

                                    Log.d("tanggal", "today = " + hariini + " date = " + date);

                                    if (date.equals(hariini)) {
                                        title = movieItemses.get(i).getNama();
                                        message = title + " rilis";

                                        notificationItem = new NotificationItem(idNotif, title, message);
                                        stackNotif.add(notificationItem);

                                        if (idNotif < maxNotif) {
                                            notification = new NotificationCompat.Builder(context)
                                                    .setContentTitle(stackNotif.get(idNotif).getJudulna())
                                                    .setContentText(stackNotif.get(idNotif).getPesan())
                                                    .setSmallIcon(R.drawable.movie_black)
                                                    .setGroup(GROUP_KEY_MOVIES)
                                                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                                    .setAutoCancel(true)
                                                    .build();
                                        } else {
                                            inboxStyle = new NotificationCompat.InboxStyle()
                                                    .addLine(stackNotif.get(idNotif).getPesan())
                                                    .addLine(stackNotif.get(idNotif-1).getPesan())
                                                    .addLine(stackNotif.get(idNotif-2).getPesan())
                                                    .setBigContentTitle("Momovie");

                                            notification = new NotificationCompat.Builder(context)
                                                    .setContentTitle(idNotif+1 + " film rilis hari ini")
                                                    .setSmallIcon(R.drawable.movie_black)
                                                    .setGroup(GROUP_KEY_MOVIES)
                                                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                                    .setGroupSummary(true)
                                                    .setStyle(inboxStyle)
                                                    .setAutoCancel(true)
                                                    .build();
                                        }

                                        idNotif++;
                                    }
                                }
                                if (idNotif != 0) {
                                    manager.notify(idNotif, notification);
                                } else {
                                    Log.d("Not found", "Tidak ada film yang dirilis hari ini");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                        @Override
                        public boolean getUseSynchronousMode() {
                            return false;
                        }
                    });
                    result.finish();
                }
            };
            thread.start();
        }
    }

    private void showNotification(Context context, String title, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        message = "Aku kangen kamu!";

        assert notificationManagerCompat != null;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , "ID")
                .setSmallIcon(R.drawable.movie_black)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId("ID");
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }

        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void setDailyReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        int requestCode = NOTIF_ID_DAILY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void setReleaseToday(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        Calendar kalendar = Calendar.getInstance();
        kalendar.set(Calendar.HOUR_OF_DAY, 7);
        kalendar.set(Calendar.MINUTE, 59);
        kalendar.set(Calendar.SECOND, 0);
        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, kalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void stopNotif(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? NOTIF_ID_DAILY : NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
