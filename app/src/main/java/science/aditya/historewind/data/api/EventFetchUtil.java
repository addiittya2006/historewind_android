package science.aditya.historewind.data.api;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import science.aditya.historewind.data.model.Digest;
import science.aditya.historewind.data.model.HistoryEvent;
import science.aditya.historewind.ui.events.EventPagerAdapter;

public class EventFetchUtil {

    private Context appContext;
    private ViewPager mPager;
    private RelativeLayout actionBar;
    private FrameLayout tintWindow;

    public EventFetchUtil(ViewPager viewPager, RelativeLayout bar, FrameLayout tint, final Context context) {
        this.appContext = context;
        this.mPager = viewPager;
        this.actionBar = bar;
        this.tintWindow = tint;
    }

    public void fetchDigest(RequestQueue rq, String BASE_URL, final String date, int tod, final EventPagerAdapter mPagerAdapter, final List<HistoryEvent> curDigest) {
        final String digestType;
        if (tod==1){
            digestType = "eve";
        } else {
            digestType = "morn";
        }
        String curURL = BASE_URL+date+"/"+digestType+"_digest.json";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, curURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        FileOutputStream outputStream;
                        FileInputStream cachedDigest = null;
                        try{
                            cachedDigest = appContext.openFileInput(date+"_"+digestType+".digest");
                        } catch (FileNotFoundException fnfe) {
                            try {
                                File[] digestfiles = appContext.getFilesDir().listFiles();
                                for (File file:digestfiles) {
                                    if (file.isFile() && file.getPath().endsWith(".digest")) {
                                        file.delete();
                                    }
                                }
                                outputStream = appContext.openFileOutput(date+"_"+digestType+".digest", Context.MODE_PRIVATE);
                                outputStream.write(jsonObject.toString().getBytes());
                                outputStream.close();
                                cachedDigest = appContext.openFileInput(date+"_"+digestType+".digest");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            BufferedReader br = new BufferedReader(new InputStreamReader(cachedDigest, "UTF-8"));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while(( line = br.readLine()) != null ) {
                                sb.append( line );
                                sb.append( '\n' );
                            }

                            Gson gson = new Gson();
                            Digest digest = gson.fromJson(sb.toString(), Digest.class);
                            curDigest.addAll(digest.getBirths());
                            curDigest.addAll(digest.getEvents());
                            curDigest.addAll(digest.getDeaths());
                            mPagerAdapter.notifyDataSetChanged();
//                            TODO: implement arrow animator
//                            arr3.setVisibility(View.GONE);
//                            arr1.setVisibility(View.GONE);
//                            arr2.setVisibility(View.GONE);
//                            customArrowAnim.stop();
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
        rq.add(request);
    }

}
