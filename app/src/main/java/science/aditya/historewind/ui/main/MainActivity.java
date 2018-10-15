package science.aditya.historewind.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import science.aditya.historewind.R;
import science.aditya.historewind.data.api.EventFetchUtil;
import science.aditya.historewind.data.model.Digest;
import science.aditya.historewind.data.model.HistoryEvent;
import science.aditya.historewind.ui.cached.CachedDigestActivity;
import science.aditya.historewind.ui.events.anim.CustomArrowAnim;
import science.aditya.historewind.ui.events.EventCardTransformer;
import science.aditya.historewind.ui.events.EventPagerAdapter;
import science.aditya.historewind.util.DateUtil;


/**
 * Created by addiittya on 13/03/17.
 */

public class MainActivity extends FragmentActivity {

    private ViewPager mPager;
    private EventPagerAdapter mPagerAdapter;
    private List<HistoryEvent> curDigest = new ArrayList<>();
    private int screenWidth;
    private RelativeLayout actionBar;
    private ImageView arr1, arr2, arr3;
    private CustomArrowAnim customArrowAnim;
    private FrameLayout tintWindow;
    private Digest digest;
    private Set<String> cached ;

    private final String BASE_URL = "https://history.aditya.science/";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;

        actionBar = (RelativeLayout) findViewById(R.id.datebar);
        actionBar.setVisibility(View.GONE);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        tintWindow = (FrameLayout) findViewById(R.id.tint);

//        TODO: implement arrow animator
//        arr1 = (ImageView) findViewById(R.id.arr1);
//        arr2 = (ImageView) findViewById(R.id.arr2);
//        arr3 = (ImageView) findViewById(R.id.arr3);

//        customArrowAnim = new CustomArrowAnim(screenWidth, arr1, arr2, arr3);
//        customArrowAnim.start();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), 2 * (getResources().getDisplayMetrics().density), curDigest);
        mPager.setAdapter(mPagerAdapter);
        mPager.setVisibility(View.GONE);

        EventCardTransformer eventCardTransformer = new EventCardTransformer(mPager, mPagerAdapter);
        eventCardTransformer.enableScaling(true);

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(false, eventCardTransformer);
        mPager.setOffscreenPageLimit(3);

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                && conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {
            Intent i = new Intent(MainActivity.this, CachedDigestActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            File cacheDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"cached");
            if(!cacheDirectory.exists()) {
                cacheDirectory.mkdirs();
            }
            startActivity(i);
        }

        final DateUtil du = new DateUtil();
        String curDate = du.getMonth()+"_"+Integer.toString(du.getDate());
        String tvCurDate = du.getMonth()+" "+Integer.toString(du.getDate());

        TextView curDateTv = (TextView) findViewById(R.id.curDate);
        curDateTv.setText(tvCurDate);

        requestQueue = Volley.newRequestQueue(this);
        EventFetchUtil ef = new EventFetchUtil(mPager, actionBar, tintWindow, getApplicationContext());
        ef.fetchDigest(requestQueue, BASE_URL, curDate, du.getTod(), mPagerAdapter, curDigest);

        ImageButton reverse = (ImageButton) findViewById(R.id.rev);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CachedDigestActivity.class);
                File cacheDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"cached");
                if(!cacheDirectory.exists()) {
                    cacheDirectory.mkdirs();
                }
                startActivity(i);
            }
        });

        final ImageButton saveToCache = (ImageButton) findViewById(R.id.download);
        final ImageView doneCaching = (ImageView) findViewById(R.id.done);


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
            editor.commit();
        }


        saveToCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream cachedDigest = openFileInput(path);
                    File cacheDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"cached");
                    if(!cacheDirectory.exists()) {
                        cacheDirectory.mkdirs();
                    }
                    File saveFile = new File( cacheDirectory.getAbsolutePath()+File.separator+path);
                    if(!saveFile.exists()){
                        saveFile.createNewFile();
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


