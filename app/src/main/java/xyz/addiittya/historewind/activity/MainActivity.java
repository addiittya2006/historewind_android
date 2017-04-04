package xyz.addiittya.historewind.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
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

import xyz.addiittya.historewind.R;
import xyz.addiittya.historewind.adapter.ScreenPagerAdapter;
import xyz.addiittya.historewind.model.Digest;
import xyz.addiittya.historewind.util.CustomPageTransformer;

/**
 * Created by addiittya on 13/03/17.
 */

public class MainActivity extends FragmentActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private final String BASE_URL = "http://104.155.208.222/digest/September_11/mag.json";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new CustomPageTransformer());

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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {

                Gson gson = new Gson();
                Digest digest;
                digest = gson.fromJson(jsonObject.toString(), Digest.class);
                Log.i("hell", digest.getDate());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.i("", "Not connected");

            }
        });
        requestQueue.add(request);
    }

}


