package com.doovj.momovie;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.doovj.momovie.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor cursor;
    private Context mContext;
    private int mAppWidgetId;
    private static final String URL_BACKDROP = "http://image.tmdb.org/t/p/w780";

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        getFavoriteMovies(mContext);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        getFavoriteMovies(mContext);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        MovieItems movieItems = getItem(i);

        Bitmap bmp;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(URL_BACKDROP + movieItems.getBackdrop())
                    .into(250, 110).get();

            //DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
            //DateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            //String tanggalrilis = movieItems.getTanggalrilis();
            //Date date = inputFormat.parse(tanggalrilis);
            //CharSequence dateRilis = outputFormat.format(date);

            rv.setImageViewBitmap(R.id.imageView, bmp);
            //rv.setTextViewText(R.id.textViewTR, dateRilis);
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error","error");
        } //catch (Exception e) {
            //e.printStackTrace();
        //}
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private MovieItems getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItems(cursor);
    }

    private void getFavoriteMovies(Context c) {
        cursor = c.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }


}
