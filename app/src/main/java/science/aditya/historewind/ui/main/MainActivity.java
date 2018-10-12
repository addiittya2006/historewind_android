package science.aditya.historewind.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.Digest;
import science.aditya.historewind.data.model.HistoryEvent;
import science.aditya.historewind.ui.anim.CustomArrowAnim;
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

        actionBar = (RelativeLayout) findViewById(R.id.bar);
        actionBar.setVisibility(View.GONE);

        tintWindow = (FrameLayout) findViewById(R.id.tint);

        arr1 = (ImageView) findViewById(R.id.arr1);
        arr2 = (ImageView) findViewById(R.id.arr2);
        arr3 = (ImageView) findViewById(R.id.arr3);

        customArrowAnim = new CustomArrowAnim(screenWidth, arr1, arr2, arr3);
        customArrowAnim.start();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), 2 * (getResources().getDisplayMetrics().density), curDigest);
        mPager.setAdapter(mPagerAdapter);
        mPager.setVisibility(View.GONE);

        EventCardTransformer eventCardTransformer = new EventCardTransformer(mPager, mPagerAdapter);
        eventCardTransformer.enableScaling(true);

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(false, eventCardTransformer);
        mPager.setOffscreenPageLimit(3);

        DateUtil du = new DateUtil();
        String curDate = du.getMonth()+"_"+Integer.toString(du.getDate());
        requestQueue = Volley.newRequestQueue(this);
        fetchDigest(curDate, du.getTod());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private void fetchDigest(String date, int tod) {
        String digestType;
        if (tod==1){
            digestType = "eve_digest.json";
        } else {
            digestType = "morn_digest.json";
        }
        String curURL = BASE_URL+date+"/"+digestType;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, curURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        FileOutputStream outputStream;
                        FileInputStream cachedDigest = null;
//                        try{
//                            cachedDigest = openFileInput("current.digest");
//                        } catch (FileNotFoundException fnfe) {
//                            Log.d("fiel", "File not found");
                        try {
                            outputStream = openFileOutput("current.digest", Context.MODE_PRIVATE);
                            outputStream.write(jsonObject.toString().getBytes());
                            outputStream.close();
                            cachedDigest = openFileInput("current.digest");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                    }
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(cachedDigest, "UTF-8"));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while(( line = br.readLine()) != null ) {
                                sb.append( line );
                                sb.append( '\n' );
                            }

                            Gson gson = new Gson();
                            digest = gson.fromJson(sb.toString(), Digest.class);
                            curDigest.addAll(digest.getBirths());
                            curDigest.addAll(digest.getEvents());
                            curDigest.addAll(digest.getDeaths());
                            mPagerAdapter.notifyDataSetChanged();
                            arr3.setVisibility(View.GONE);
                            arr1.setVisibility(View.GONE);
                            arr2.setVisibility(View.GONE);
                            customArrowAnim.stop();
                            actionBar.setVisibility(View.VISIBLE);
                            mPager.setVisibility(View.VISIBLE);
                            tintWindow.setVisibility(View.GONE);

                        } catch (Exception e){
                            Log.d("Error", "Some Internal Error Occurred"+e.toString());
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i("", "Not connected");
                    }
                });
        requestQueue.add(request);
    }
}


