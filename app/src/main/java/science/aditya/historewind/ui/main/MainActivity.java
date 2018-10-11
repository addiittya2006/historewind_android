package science.aditya.historewind.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

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
import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.Digest;
import science.aditya.historewind.data.model.Event;
import science.aditya.historewind.data.model.HistoryEvent;
import science.aditya.historewind.ui.events.EventFragment;
import science.aditya.historewind.ui.events.EventPagerAdapter;
import science.aditya.historewind.ui.util.CustomCardTransformer;


/**
 * Created by addiittya on 13/03/17.
 */

public class MainActivity extends FragmentActivity {

    private ViewPager mPager;
    private EventPagerAdapter mPagerAdapter;
    private List<HistoryEvent> curDigest = new ArrayList<>();

    private final String BASE_URL = "https://history.aditya.science/September_11/morn_digest.json";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new EventPagerAdapter(getApplicationContext(), getSupportFragmentManager(), 2 * (getResources().getDisplayMetrics().density), curDigest);
        mPager.setAdapter(mPagerAdapter);

        CustomCardTransformer customCardTransformer = new CustomCardTransformer(mPager, mPagerAdapter);
        customCardTransformer.enableScaling(true);

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(false, customCardTransformer);
        mPager.setOffscreenPageLimit(3);


        requestQueue = Volley.newRequestQueue(this);
        fetchDigest();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private void fetchDigest() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        FileOutputStream outputStream;
                        FileInputStream cachedDigest = null;
                        try{
                            cachedDigest = openFileInput("current.digest");
                        } catch (FileNotFoundException fnfe) {
                            Log.d("fiel", "File not found");
                        try {
                            outputStream = openFileOutput("current.digest", Context.MODE_PRIVATE);
                            outputStream.write(jsonObject.toString().getBytes());
                            outputStream.close();
                            cachedDigest = openFileInput("current.digest");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }}
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(cachedDigest, "UTF-8"));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while(( line = br.readLine()) != null ) {
                                sb.append( line );
                                sb.append( '\n' );
                            }

                            Gson gson = new Gson();
                            Digest digest;
                            digest = gson.fromJson(sb.toString(), Digest.class);
                            curDigest.addAll(digest.getBirths());
                            curDigest.addAll(digest.getEvents());
                            curDigest.addAll(digest.getDeaths());
//                            mPagerAdapter.clearPrev();
//                            for(int i = 0; i< 10; i++) {
//
//                            }

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
        mPagerAdapter.notifyDataSetChanged();
    }
}


