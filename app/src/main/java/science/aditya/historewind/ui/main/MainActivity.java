package science.aditya.historewind.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import science.aditya.historewind.R;
import science.aditya.historewind.data.api.EventFetchUtil;
import science.aditya.historewind.data.model.HistoryEvent;
import science.aditya.historewind.ui.cached.CachedDigestActivity;
import science.aditya.historewind.ui.main.events.EventPagerAdapter;
import science.aditya.historewind.ui.main.events.EventPagerTransformer;
import science.aditya.historewind.util.DateUtil;

/**
 * Created by addiittya on 13/03/17.
 */

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private List<HistoryEvent> curDigest = new ArrayList<>();
    private Set<String> cached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBack));
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        mPager = findViewById(R.id.pager);

        float density = getResources().getDisplayMetrics().density;
        float pagerMargin = 40*density;
        float pageMargin = 10*density;

        Point screen = new Point();
        getWindowManager().getDefaultDisplay().getSize(screen);

        float pagerDrawOffset = (pagerMargin)/(screen.x - 2*pagerMargin);
        EventPagerTransformer eventPagerTransformer = new EventPagerTransformer(pagerDrawOffset);

        EventPagerAdapter mPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), curDigest);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageMargin((int) pageMargin);
        mPager.setPageTransformer(false, eventPagerTransformer);

        mPager.setVisibility(View.GONE);

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                && conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            Intent i = new Intent(MainActivity.this, CachedDigestActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            File cacheDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"cached");
            if(!cacheDirectory.exists())
                if (cacheDirectory.mkdirs()) {
                    Log.d("Directories", "");
                }
            startActivity(i);
        }

        final DateUtil du = new DateUtil();
        String curDate = du.getMonth()+"_"+Integer.toString(du.getDate());
        String tvCurDate = du.getMonth()+" "+Integer.toString(du.getDate());

        TextView curDateTv = findViewById(R.id.curDate);
        curDateTv.setText(tvCurDate);

        new EventFetchUtil(this, mPager, mPagerAdapter, curDigest).fetchDigest(curDate, du.getTod());

        ImageButton reverse = findViewById(R.id.rev);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CachedDigestActivity.class);
                File cacheDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"cached");
                if(!cacheDirectory.exists())
                    if (cacheDirectory.mkdirs()) {
                        Log.d("Directories", "");
                    }
                startActivity(i);
            }
        });

        final ImageButton saveToCache = findViewById(R.id.download);
        final ImageView doneCaching = findViewById(R.id.done);


        String timeOfDay;
        if(du.getTod() == 0){
            timeOfDay = "morn";
        } else {
            timeOfDay = "eve";
        }
        final String path = du.getMonth()+"_"+Integer.toString(du.getDate())+"_"+timeOfDay+".digest";


        cached = prefs.getStringSet("cached", new HashSet<String>());
        if(cached!= null && cached.contains(path)) {
            saveToCache.setVisibility(View.GONE);
            doneCaching.setVisibility(View.VISIBLE);
        } else {
            editor.putStringSet("cached", cached);
            editor.apply();
        }


        saveToCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream cachedDigest = openFileInput(path);
                    File cacheDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"cached");
                    if (!cacheDirectory.exists())
                        if (cacheDirectory.mkdirs()) {
                            Log.d("Directories", "");
                        }
                    File saveFile = new File( cacheDirectory.getAbsolutePath()+File.separator+path);
                    if (!saveFile.exists())
                        if (saveFile.createNewFile()) {
                            Log.d("Directories", "");
                        }
                    FileOutputStream saveFileStream = new FileOutputStream(saveFile, false);

                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = cachedDigest.read(buffer)) != -1) {
                        saveFileStream.write(buffer, 0, read);
                    }
                    saveFileStream.close();
                    cachedDigest.close();

                    cached.add(path);
                    editor.remove("cached").commit();
                    editor.putStringSet("cached", cached);
                    editor.commit();

                    saveToCache.setVisibility(View.GONE);
                    doneCaching.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(0);
        }
    }

}


